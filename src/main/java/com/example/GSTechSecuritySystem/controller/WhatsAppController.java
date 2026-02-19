package com.example.GSTechSecuritySystem.controller;

import com.example.GSTechSecuritySystem.model.Invoice;
import com.example.GSTechSecuritySystem.service.GupshupInvoiceTextService;
import com.example.GSTechSecuritySystem.service.WhatsAppSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppController {

    @Autowired
    private final GupshupInvoiceTextService invoiceTextService;

    public WhatsAppController(GupshupInvoiceTextService invoiceTextService) {
        this.invoiceTextService = invoiceTextService;
    }

    @PostMapping("/send-invoice-text")
    public String sendInvoice(@RequestBody Invoice invoice) {
        LocalDate localDate = LocalDate.now();
        return invoiceTextService.sendInvoiceWithItems(
                invoice.getMobileNumber(),
                invoice.getCustomerName(),
                invoice.getInvoiceNumber(),
                String.valueOf(localDate),
                invoice.getItems().toString(),
                String.valueOf(invoice.getTotalAmount())
        );
    }

}
