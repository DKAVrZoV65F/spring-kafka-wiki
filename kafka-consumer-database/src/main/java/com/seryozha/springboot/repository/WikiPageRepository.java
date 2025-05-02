package com.seryozha.springboot.repository;

import com.seryozha.springboot.entity.WikiPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiPageRepository extends JpaRepository<WikiPage, Long> {
    WikiPage findByTitle(String title);
}
