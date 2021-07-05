package com.jtech.jhome;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HPageContentRepository extends CrudRepository<HPageContent, Long> {
    HPageContent findById(long id);
}