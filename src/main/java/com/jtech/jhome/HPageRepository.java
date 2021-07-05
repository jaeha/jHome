package com.jtech.jhome;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HPageRepository extends CrudRepository<HPage, Long> {
    List<HPage> findByTitle(String title);
    List<HPage> findAll();
    HPage findById(long id);

    Page<HPage> findAll(Pageable pageble);
}
