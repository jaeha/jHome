package com.jtech.jhome;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
    List<Book> findAll();
    Book findById(long id);
    List<Book> findByFileName(String fileName);
    List<Book> findByUrl(String url);
}
