package com.jtech.jhome;

import javax.persistence.*;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "title", length = 1024)
    private String title;

    @Column(name = "singer", length = 256)
    private String singer;

    @Column(name = "composer", length = 256)
    private String composer;

    @Column(name = "country", length = 128)
    private String country;

    @Column(name = "album", length = 256)
    private String album;

    @Column(name = "coverUrl", length = 1024)
    private String coverUrl;

    @Column(name = "releaseYear")
    private int releaseYear;

    @Column(name = "isFavorite")
    private boolean isFavorite;

    @Column(name = "tag", length = 256)
    private String tag;

    @Column(name = "note", length = 1024)
    private String note;

    @Column(name = "modifyDate")
    private Date modifyDate;

    //scaning...
    @Column(nullable = false, name = "fileName", length = 1024)
    private String fileName;

    @Column(name = "fileDate")
    private Date fileDate;

    @Column(nullable = false, unique = true, name = "url", length = 1024)
    private String url;

    // construct
    protected Music() {}

    public Music(String fileName, Date fileDate, String url) {
        this.title = fileName;
        this.singer = "";
        this.composer = "";
        this.country = "";
        this.album = "";
        this.coverUrl = "";
        this.releaseYear = fileDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
        this.isFavorite = false;
        this.tag = "";
        this.note = "";
        this.modifyDate = new Date();
        this.fileName = fileName;
        this.fileDate = fileDate;
        this.url = url;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getSinger() { return singer; }
    public String getComposer() { return composer; }
    public String getCountry() { return country; }
    public String getAlbum() { return album; }
    public String getCoverUrl() { return coverUrl; }
    public int getReleaseYear() { return releaseYear; }
    public boolean isFavorite() { return isFavorite; }
    public String getTag() { return tag; }
    public String getNote() { return note; }
    public Date getModifyDate() { return modifyDate; }
    public String getFileName() { return fileName; }
    public Date getFileDate() { return fileDate; }
    public String getUrl() { return url; }
}