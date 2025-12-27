package com.example.GSTechSecuritySystem.repository;

import com.example.GSTechSecuritySystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCompanyName(String companyName);

    Product findByProductName(String productName);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) = LOWER(:name)")
    Product findByName(@Param("name") String name);
}