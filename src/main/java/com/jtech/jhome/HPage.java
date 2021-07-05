package com.jtech.jhome;

import javax.persistence.*;
import java.util.Date;

@Entity
public class HPage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, name= "title", length = 1024)
    private String title;

    @Column(name="contentId")
    private Long contentId;

    @Column(name="author")
    private String author;

    @Column(name="modifyDate")
    private Date modifyDate;

    protected HPage() {}

    public HPage(String title, long contentId, String author) {
        this.title = title;
        this.contentId = contentId;
        this.author = author;
        this.modifyDate = new Date();
    }

    @Override
    public String toString() {
        return "HPage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contentId='" + contentId + '\'' +
                ", author='" + author + '\'' +
                ", modifyDate=" + modifyDate +
                '}';
    }

    public Long getId() {
        return id;
    }
    public String getTitle() { return title; }
    public Long getContentId() { return contentId; }
    public Date getModifyDate() { return modifyDate; }
    public String getAuthor() { return author; }

    public void setId(long id) { this.id = id; }
    public void setData (String title, long contentId, String author) {
        this.title = title;
        this.contentId = contentId;
        this.author = author;
        this.modifyDate = new Date();
    }
}
