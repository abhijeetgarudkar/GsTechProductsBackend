package com.example.GSTechSecuritySystem.Do;

public class CartRequestDTO {

    private Product product;
    private int quantity;

    public CartRequestDTO() {
    }

    public CartRequestDTO(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartRequestDTO{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
