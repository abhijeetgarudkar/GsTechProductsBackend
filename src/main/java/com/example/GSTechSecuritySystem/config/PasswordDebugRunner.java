package com.example.GSTechSecuritySystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordDebugRunner {

    @Bean
    CommandLineRunner printHash(PasswordEncoder encoder) {
        return args -> {
            System.out.println(">>> GENERATED HASH = " + encoder.encode("admin123"));
        };
    }
}
