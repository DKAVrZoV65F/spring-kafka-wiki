package com.seryozha.springboot.repository;

import com.seryozha.springboot.entity.WikiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiUserRepository extends JpaRepository<WikiUser, Long> {
    WikiUser findByUsername(String username);
}
