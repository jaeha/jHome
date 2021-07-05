package com.jtech.jhome;

import org.codehaus.jettison.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/scan")
public class ScanController {
    private static final Logger log = LoggerFactory.getLogger(ScanController.class);

    private enum RepoType { VIDEO, MUSIC, PHOTO, BOOK, OTHER }
    private RepoType repoType = RepoType.OTHER;

    private String currentBaseDir;
    private int scanTotal = 100000;
    private int scanProgress = 0;
    private enum StatusType {INIT, START, STOP, COMPLETE};
    private StatusType scanStatus = StatusType.INIT;

    // application.properties
    @Value("${jhome.video.path}")
    private String videoPath;

    @Value("${jhome.music.path}")
    private String musicPath;

    @Value("${jhome.photo.path}")
    private String photoPath;

    @Value("${jhome.book.path}")
    private String bookPath;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping("")
    public String getScan() { return "scan"; }

    static class Holder {
        String[] dirs;
        RepoType repoType;

        public String[] getDirs() { return dirs; }
        public RepoType getRepoType() { return repoType; }

        @Override
        public String toString() {
            return "Holder{" +
                    "dirs=" + Arrays.toString(dirs) +
                    ", repoType=" + repoType +
                    '}';
        }

        public void setRepoType(int repoType) {
            this.repoType = RepoType.values()[repoType];
        }
    }

    @PostMapping("/start")
    public @ResponseBody
    String postStart(@RequestBody Holder holder) throws IOException {
        log.info("parameters: " + holder.toString());

        // estimate the max progress
        File disk = new File(repo2path(holder.getRepoType()));
        int usedDiskSpace = (int) ((disk.getTotalSpace() - disk.getFreeSpace())/(1024*1024));
        log.info("disk space used: " + usedDiskSpace );

        scanTotal = (usedDiskSpace <= 0) ? 100000 : usedDiskSpace; /// windows cannot get disk space size
        scanProgress = 0;
        scanStatus = StatusType.START;


        for (String d : holder.getDirs()) {
            log.info("Scaning dir: " + d);
            scanFiles(repo2path(holder.getRepoType()) + "/" + d, holder.getRepoType());
        }

        //scanProgress = 100;
        if (scanStatus == StatusType.STOP) {
            log.info("scan files was stopped!!");
        } else {
            scanStatus = StatusType.COMPLETE;
            log.info("scan files completed!!");
        }

        return null;
    }

    @GetMapping("/stop")
    public @ResponseBody
    String getStop() {
        scanProgress = 0;
        scanTotal = 0;
        scanStatus = StatusType.STOP;

        return null;
    }


///// API

    @PostMapping("/api/subdirs")
    public @ResponseBody
    String[] postSubDirs(@RequestBody Holder holder) {
        return subDirs(repo2path(holder.getRepoType()));
    }

    @GetMapping("/api/progress")
    public @ResponseBody
    int getProgress() {
        int progress = 0;
        switch (scanStatus) {
            case START:
                progress = ((scanProgress * 100) / scanTotal) + 1;
                break;
            case COMPLETE:
                progress = 100;
                break;
            case STOP:
        }

        return progress;
    }

//// Functions
    public void scanFiles(String path, RepoType repoType) throws IOException {
        String url2;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path))) {
        for (Path p : stream) {
                if (scanStatus == StatusType.STOP)
                    break;
                if (!Files.isDirectory(p) && !Files.isHidden(p)) {
                    //String url = p.toUri().toString().replaceFirst("file:///" + mediaPath, "");
                    String url = p.toUri().toString();
                    String fname = p.getFileName().toString();
                    Date fileDate = new Date(Files.readAttributes(p, BasicFileAttributes.class).creationTime().toMillis());
                    switch (repoType) {
                        case VIDEO:
                            url2 = url.substring(url.indexOf(videoPath) + videoPath.length());
                            if (videoRepository.findByUrl(url2).isEmpty())
                                videoRepository.save(new Video(fname, fileDate, url2));
                            break;
                        case MUSIC:
                            url2 = url.substring(url.indexOf(musicPath) + musicPath.length());
                            if (musicRepository.findByUrl(url2).isEmpty())
                                musicRepository.save(new Music(fname, fileDate, url2));
                            break;
                        case PHOTO:
                            url2 = url.substring(url.indexOf(photoPath) + photoPath.length());
                            if (photoRepository.findByUrl(url2).isEmpty())
                                photoRepository.save(new Photo(fname, fileDate, url2));
                            break;
                        case BOOK:
                            url2 = url.substring(url.indexOf(bookPath) + bookPath.length());
                            if (bookRepository.findByUrl(url2).isEmpty())
                                bookRepository.save(new Book(fname, fileDate, url2));
                            break;
                        case OTHER:
                    }
                    scanProgress++;
                } else if (Files.isDirectory(p))  {
                    scanFiles(p.toAbsolutePath().toString(), repoType);
                }
            }
        }
    }

    private String repo2path(RepoType repoType) {
        String path="";
        switch (repoType) {
            case VIDEO:
                path = videoPath;
                break;
            case MUSIC:
                path = musicPath;
                break;
            case PHOTO:
                path = photoPath;
                break;
            case BOOK:
                path = bookPath;
                break;
        }
     //   log.info("repo2path: " + path);
        return path;
    }

    public String[] subDirs(String path) {
        // get the list of directories
        String[] directories = new File(path).list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return new File(file, name).isDirectory();
            }
        });

        return directories;
    }


/*
    private Media.MediaType getMediaType(String fname) {
        String ext = fname.substring(fname.lastIndexOf(".")+1);
        Media.MediaType type = Media.MediaType.OTHER;  // default

        if (!ext.isEmpty()) {
            if (Arrays.asList(videoExt).contains(ext)) {
                type = Media.MediaType.VIDEO;
            } else if (Arrays.asList(musicExt).contains(ext)) {
                type = Media.MediaType.MUSIC;
            } else if (Arrays.asList(photoExt).contains(ext)) {
                type = Media.MediaType.PHOTO;
            } else if (Arrays.asList(bookExt).contains(ext)) {
                type = Media.MediaType.BOOK;
            }
        }

        return type;
    }

    public ModelAndView form(@ModelAttribute Task task) {
        log.info("task form()");
        this.task = task;
        ModelAndView model = new ModelAndView("task");
        task.execute();
        model.addObject("task", this.task);
        return model;
    }
*/
}
