package com.example.GSTechSecuritySystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GupshupInvoiceTextService {

    @Value("${gupshup.api-key}")
    private String apiKey;

    @Value("${gupshup.source-number}")
    private String sourceNumber;

    private final RestTemplate restTemplate;

    public GupshupInvoiceTextService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendInvoiceWithItems(
            String customerNumber,
            String customerName,
            String invoiceNo,
            String invoiceDate,
            String lineItemsText,
            String totalAmount
    ) {

        String url = "https://api.gupshup.io/sm/api/v1/msg";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("apikey", apiKey);

        String templateJson = """
        {
          "id": "invoice_with_items",
          "params": ["%s", "%s", "%s", "%s", "%s"]
        }
        """.formatted(
                customerName,
                invoiceNo,
                invoiceDate,
                lineItemsText,
                totalAmount
        );

        String body =
                "channel=whatsapp" +
                        "&source=" + sourceNumber +
                        "&destination=" + customerNumber +
                        "&template=" + templateJson;

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        return restTemplate
                .postForEntity(url, request, String.class)
                .getBody();
    }
}

