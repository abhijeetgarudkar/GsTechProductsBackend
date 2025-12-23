package com.example.GSTechSecuritySystem.Repository;

import java.util.Optional;

import com.example.GSTechSecuritySystem.Do.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}