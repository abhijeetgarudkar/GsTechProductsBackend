package com.example.GSTechSecuritySystem.service;

import com.example.GSTechSecuritySystem.Do.CartItems;
import com.example.GSTechSecuritySystem.Do.CartRequestDTO;
import com.example.GSTechSecuritySystem.Do.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final Map<String, CartItems> cartItems = new HashMap<>();

    @Value("${gstValue}")
    double gstValue;

    public List<CartItems> addToCart(Product product, int quantity) {
        String productName = product.getProductName();

        CartItems existingItem = cartItems.get(productName);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantity;
            existingItem.setQuantity(newQuantity);
            existingItem.setTotalPrice(newQuantity * existingItem.getProduct().getProductPrice());
        } else {
            CartItems newItem = new CartItems(product, quantity, product.getProductPrice() * quantity);
            cartItems.put(productName, newItem);
        }

        return new ArrayList<>(cartItems.values());
    }

    public double calculateCartTotal() {
        double cartTotal = cartItems.values()
                .stream()
                .mapToDouble(CartItems::getTotalPrice)
                .sum();

        double cartTotalWithGSt = cartTotal * (double) (gstValue/100) + cartTotal;
        System.out.println("cartTotal.."+cartTotal);
        System.out.println("GSt.."+gstValue);
        System.out.println("cartTotal after adding GST.."+cartTotalWithGSt);
        return cartTotalWithGSt;
    }

    public List<CartItems> getAllItems() {
        return new ArrayList<>(cartItems.values());
    }

    public void clearCart() {
        cartItems.clear();
    }
}
