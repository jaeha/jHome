package com.jtech.jhome;

import javax.persistence.*;
import java.util.Date;

@Entity
public class HPageContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name="content")
    private String content;

    @Column(name="modifyDate")
    private Date modifyDate;

    protected HPageContent() {}

    public HPageContent(String content) {
        this.content = content;
        this.modifyDate = new Date();
    }

    @Override
    public String toString() {
        return "HPageContent{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", modifyDate=" + modifyDate +
                '}';
    }

    public Long getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setData(String content) {
        this.content = content;
        this.modifyDate = new Date();
    }
}
