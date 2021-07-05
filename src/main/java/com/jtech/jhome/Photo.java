package com.jtech.jhome;

import javax.persistence.*;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "title", length = 1024)
    private String title;

    @Column(name = "person", length = 256)
    private String person;

    @Column(name = "location", length = 256)
    private String location;

    @Column(name = "event", length = 256)
    private String event;

    @Column(name = "gps", length = 256)
    private String gps;

    @Column(name = "takenYear")
    private int takenYear;

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
    protected Photo() {}

    public Photo(String fileName, Date fileDate, String url) {
        this.title = fileName;
        this.person = "";
        this.location = "";
        this.event = "";
        this.gps = "";
        this.takenYear = fileDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
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
    public String getPerson() { return person; }
    public String getLocation() { return location; }
    public String getEvent() { return event; }
    public String getGps() { return gps; }
    public int getTakenYear() { return takenYear; }
    public boolean isFavorite() { return isFavorite; }
    public String getTag() { return tag; }
    public String getNote() { return note; }
    public Date getModifyDate() { return modifyDate; }
    public String getFileName() { return fileName; }
    public Date getFileDate() { return fileDate; }
    public String getUrl() { return url; }
}
