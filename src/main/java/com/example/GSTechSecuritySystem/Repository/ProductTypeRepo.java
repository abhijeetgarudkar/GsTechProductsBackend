package com.example.GSTechSecuritySystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.GSTechSecuritySystem.Do.Producttypes;

@Repository
public interface ProductTypeRepo extends JpaRepository<Producttypes, Integer> {
    // saveAll() and findAll() already included
}
