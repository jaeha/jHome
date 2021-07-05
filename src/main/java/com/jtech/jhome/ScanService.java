package com.jtech.jhome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

@Service
public class ScanService {

    private static final Logger log = LoggerFactory.getLogger(ScanService.class);

    public String[] getSubDirs(String path) {
        // get the list of directories
        String[] dirs = new File(path).list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return new File(file, name).isDirectory();
            }
        });

        return dirs;
    }

    public void scanFiles(String path) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path))) {
            for (Path p : stream) {
              //  if (scanStatus == ScanController.StatusType.STOP)
              //      break;
                if (!Files.isDirectory(p) && !Files.isHidden(p)) {
                    //String url = path.toUri().toString().replaceFirst("file:///" + mediaPath, "");
                    String url = p.toUri().toString();
                 //   String url2 = url.substring(url.indexOf(mediaPath) + mediaPath.length());
                    String fname = p.getFileName().toString();
                    Date fileDate = new Date(Files.readAttributes(p, BasicFileAttributes.class).creationTime().toMillis());
                    log.info("[scanFiles]: " + url);
                   // if (repository.findByFileName(fname).isEmpty()) {
                   //     repository.save(new Media(getMediaType(fname), fname, url2, currentBaseDir, fileDate)); //, new Date()));
                   // }
                   // scanProgress++;
                } else if (Files.isDirectory(p))  {
                    scanFiles(p.toAbsolutePath().toString());
                }
            }
        }
    }
}
