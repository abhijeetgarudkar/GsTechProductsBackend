package com.example.GSTechSecuritySystem.controller;

import com.example.GSTechSecuritySystem.model.Invoice;
import com.example.GSTechSecuritySystem.model.Order;
import com.example.GSTechSecuritySystem.model.User;
import com.example.GSTechSecuritySystem.repository.UserRepository;
import com.example.GSTechSecuritySystem.service.CartService;
import com.example.GSTechSecuritySystem.service.GupshupInvoiceTextService;
import com.example.GSTechSecuritySystem.service.OrderService;
import com.example.GSTechSecuritySystem.service.WhatsAppSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppController {

    private final GupshupInvoiceTextService invoiceTextService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    public WhatsAppController(GupshupInvoiceTextService invoiceTextService) {
        this.invoiceTextService = invoiceTextService;
    }

    @PostMapping("/send-invoice-text")
    public ResponseEntity<?> sendInvoice(@RequestBody Invoice invoice) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        String lineItems = invoice.getItems().stream()
                .map(item -> item.getProduct().getProductName() + " (" + item.getQuantity() + " x " + item.getTotalPrice() + ") = " + item.getTotalPrice())
                .collect(Collectors.joining("\n"));

        String response = invoiceTextService.sendInvoiceWithItems(
                invoice.getMobileNumber(),
                invoice.getCustomerName(),
                invoice.getInvoiceNumber(),
                formattedDate,
                lineItems,
                String.valueOf(invoice.getTotalAmount())
        );

        User user = userRepository.findByUsername(invoice.getUser())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + invoice.getUser()));
        Long userId = user.getId();

        // 3. Save order to database
        Order savedOrder = orderService.saveOrder(
                userId,
                invoice.getUser(),
                invoice.getCustomerName(),
                invoice.getMobileNumber(),
                invoice.getAddress(),
                invoice.getItems(),
                invoice.getTotalAmount().doubleValue(),
                "Confirmed");

        cartService.clearCart();

        return ResponseEntity.ok(response);
    }
}

