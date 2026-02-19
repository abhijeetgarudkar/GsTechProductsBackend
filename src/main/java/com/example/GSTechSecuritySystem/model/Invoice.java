package com.example.GSTechSecuritySystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {

    private String invoiceNumber;
    private String customerName;
    private String mobileNumber;
    private String address;
    private List<CartItems> items;
    private BigDecimal totalAmount;
    private String user;

    public Invoice() {}

    public Invoice(String invoiceNumber, String customerName, String mobileNumber, String address, List<CartItems> items, BigDecimal totalAmount, String user) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.items = items;
        this.totalAmount = totalAmount;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<CartItems> getItems() {
        return items;
    }

    public void setItems(List<CartItems> items) {
        this.items = items;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}

