package com.example.GSTechSecuritySystem.service;

import com.example.GSTechSecuritySystem.model.CartItems;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceMessageBuilder {

    public String buildMessage(
            String customerName,
            List<CartItems> items,
            double total) {
        StringBuilder sb = new StringBuilder();
        sb.append("🧾 *Invoice Generated*\n\n");
        sb.append("Customer: ").append(customerName).append("\n\n");

        for (CartItems item : items) {
            sb.append(item.getProduct().getProductName())
                    .append(" x ")
                    .append(item.getQuantity())
                    .append(" = ₹")
                    .append(item.getTotalPrice())
                    .append("\n");
        }

        sb.append("\nTotal Amount: ₹").append(total);
        sb.append("\n\nThank you for shopping with us!");

        return sb.toString();
    }
}
