package com.jtech.jhome;

import javax.persistence.*;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name= "title", length = 1024)
    private String title;

    @Column(name= "actor", length = 256)
    private String actor;

    @Column(name= "country", length = 128)
    private String country;

    @Column(name= "thumnailUrl", length = 1024)
    private String thumnailUrl;

    @Column(name= "trailerUrl", length = 1024)
    private String trailerUrl;

    @Column(name="releaseYear")
    private int releaseYear;

    @Column(name= "isFavorite")
    private boolean isFavorite;

    @Column(name= "tag", length = 256)
    private String tag;

    @Column(name= "note", length = 1024)
    private String note;

    @Column(name="modifyDate")
    private Date modifyDate;

    //scaning...
    @Column(nullable = false, name= "fileName", length = 1024)
    private String fileName;

    @Column(name="fileDate")
    private Date fileDate;

    @Column(nullable = false, unique = true, name="url", length = 1024)
    private String url;

    // construct
    protected Video() {}

    public Video(String fileName, Date fileDate, String url) {
        this.title = fileName;
        this.actor = "";
        this.country = "";
        this.thumnailUrl = "";
        this.trailerUrl = "";
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
    public String getActor() { return actor; }
    public String getCountry() { return country; }
    public String getThumnail() { return thumnailUrl; }
    public String getTrailer() { return trailerUrl; }
    public int getReleaseYear() { return releaseYear; }
    public boolean isFavorite() { return isFavorite; }
    public String getTag() { return tag; }
    public String getNote() { return note; }
    public Date getModifyDate() { return modifyDate; }
    public String getFileName() { return fileName; }
    public Date getFileDate() { return fileDate; }
    public String getUrl() { return url; }
}
