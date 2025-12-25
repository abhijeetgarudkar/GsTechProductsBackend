package com.example.GSTechSecuritySystem.Do;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {

    private String orderId;
    private LocalDateTime orderDate;
    private String customerName;
    private String mobileNumber;
    private String address;
    private double totalAmount;
    private String status;
    private List<OrderItemDTO> items;

    // Constructors
    public OrderResponseDTO() {
    }

    public OrderResponseDTO(String orderId, LocalDateTime orderDate, String customerName,
            String mobileNumber, String address, double totalAmount,
            String status, List<OrderItemDTO> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.totalAmount = totalAmount;
        this.status = status;
        this.items = items;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    // Inner class for order items
    public static class OrderItemDTO {
        private String productName;
        private int quantity;
        private double totalPrice;

        public OrderItemDTO() {
        }

        public OrderItemDTO(String productName, int quantity, double totalPrice) {
            this.productName = productName;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }
    }
}
