package com.example.GSTechSecuritySystem.model;

import java.util.List;

public class Invoice {
    private String invoiceNumber;
    private String customerName;
    private String mobileNumber;
    private String address;
    private List<CartItems> items;
    private double totalAmount;

    public Invoice(String invoiceNumber, String customerName, String mobileNumber, String address,
            List<CartItems> items, double totalAmount) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<CartItems> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setItems(List<CartItems> items) {
        this.items = items;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
