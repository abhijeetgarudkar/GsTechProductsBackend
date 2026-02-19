package com.example.GSTechSecuritySystem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GsTechSecuritySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsTechSecuritySystemApplication.class, args);
	}

}
