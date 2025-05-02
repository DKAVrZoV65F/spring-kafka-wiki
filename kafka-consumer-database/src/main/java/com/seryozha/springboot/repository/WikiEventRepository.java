package com.seryozha.springboot.repository;

import com.seryozha.springboot.entity.WikiEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiEventRepository extends JpaRepository<WikiEvent, Long> { }
