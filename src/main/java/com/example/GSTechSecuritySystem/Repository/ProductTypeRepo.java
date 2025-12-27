package com.example.GSTechSecuritySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.GSTechSecuritySystem.model.Producttypes;

@Repository
public interface ProductTypeRepo extends JpaRepository<Producttypes, Integer> {
    // saveAll() and findAll() already included
}
