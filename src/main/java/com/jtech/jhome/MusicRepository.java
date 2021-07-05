package com.jtech.jhome;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MusicRepository extends CrudRepository<Music, Long> {
    List<Music> findByTitle(String title);
    List<Music> findAll();
    Music findById(long id);
    List<Music> findByFileName(String fileName);
    List<Music> findByUrl(String url);
}
