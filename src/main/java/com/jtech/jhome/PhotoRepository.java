package com.jtech.jhome;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
    List<Photo> findByTitle(String title);
    List<Photo> findAll();
    Photo findById(long id);
    List<Photo> findByFileName(String fileName);
    List<Photo> findByUrl(String url);
}
