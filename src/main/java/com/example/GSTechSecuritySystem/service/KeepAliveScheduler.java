package com.example.GSTechSecuritySystem.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveScheduler {

    private final RestTemplate restTemplate = new RestTemplate();

    // every 5 minutes
    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void keepServerAlive() {
        try {
            String url = "https://gstechproductsbackend.onrender.com/health";

            String response = restTemplate.getForObject(url, String.class);

        } catch (Exception e) {
            System.out.println("KeepAlive Ping Failed: " + e.getMessage());
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void warmup() {
        System.out.println("Warmup Triggered...");
        keepServerAlive();
    }

}

