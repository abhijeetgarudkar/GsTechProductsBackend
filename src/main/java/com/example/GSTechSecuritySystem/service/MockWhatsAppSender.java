package com.example.GSTechSecuritySystem.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class MockWhatsAppSender implements WhatsAppSender {

    @Override
    public void sendText(String to, String message) {
        System.out.println("[MOCK] WhatsApp -> " + to + " : " + message);
    }
}

