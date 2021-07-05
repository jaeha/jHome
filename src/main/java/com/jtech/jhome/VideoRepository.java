package com.jtech.jhome;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoRepository extends CrudRepository<Video, Long> {
    List<Video> findByTitle(String title);
    List<Video> findAll();
    Video findById(long id);
    List<Video> findByFileName(String fileName);
    List<Video> findByUrl(String url);
}
