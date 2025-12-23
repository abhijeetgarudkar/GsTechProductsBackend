package com.example.GSTechSecuritySystem.controller;

import com.example.GSTechSecuritySystem.service.WhatsAppSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppController {

    private final WhatsAppSender sender;

    public WhatsAppController(WhatsAppSender sender) {
        this.sender = sender;
    }

    @PostMapping("/send")
    public String send() {
        sender.sendText(
                "91XXXXXXXXXX",
                "Hello! Message sent from Spring Boot"
        );
        return "Message triggered";
    }
}
