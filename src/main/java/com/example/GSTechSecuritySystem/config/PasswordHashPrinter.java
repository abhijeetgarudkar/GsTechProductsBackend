package com.example.GSTechSecuritySystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordHashPrinter {

    @Bean
    CommandLineRunner printPasswordHash(PasswordEncoder encoder) {
        return args -> {
            System.out.println(">>> GENERATED HASH FOR admin123 = "
                    + encoder.encode("admin123"));
        };
    }
}
