package com.example.GSTechSecuritySystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("prod")
public class CloudApiWhatsAppSender implements WhatsAppSender {

    @Value("${whatsapp.api-base-url}")
    private String baseUrl;

    @Value("${whatsapp.phone-number-id}")
    private String phoneNumberId;

    @Value("${whatsapp.access-token}")
    private String accessToken;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendText(String to, String message) {

        String url = baseUrl + "/" + phoneNumberId + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String payload = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "text",
          "text": {
            "body": "%s"
          }
        }
        """.formatted(to, message);

        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}
