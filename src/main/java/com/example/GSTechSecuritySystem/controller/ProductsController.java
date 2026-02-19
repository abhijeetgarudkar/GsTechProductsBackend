package com.example.GSTechSecuritySystem.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.GSTechSecuritySystem.model.*;
import com.example.GSTechSecuritySystem.service.AuthService;
import com.example.GSTechSecuritySystem.service.CartService;
import com.example.GSTechSecuritySystem.service.InvoiceMessageBuilder;
import com.example.GSTechSecuritySystem.service.OrderService;
import com.example.GSTechSecuritySystem.service.ProductService;
import com.example.GSTechSecuritySystem.service.ProductTypesService;
import com.example.GSTechSecuritySystem.service.WhatsAppSender;
import com.example.GSTechSecuritySystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gstechsecurity")
public class ProductsController {

    @Autowired
    ProductTypesService productTypesService;

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Autowired
    AuthService authService;

    @Autowired
    private WhatsAppSender whatsAppSender;

    @Autowired
    private InvoiceMessageBuilder invoiceMessageBuilder;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    public ProductsController(ProductTypesService productTypesService, AuthService authService) {
        this.productTypesService = productTypesService;
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody LoginRequest request) {
        LoginResponse resp = authService.loginAsAdmin(request);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest request) {
        System.out.println(">>> CONTROLLER HIT: /user/login");
        System.out.println(">>> REQUEST USERNAME = [" + request.getUsername() + "]");
        LoginResponse resp = authService.loginAsUser(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/productTypes")
    public List<Producttypes> getAllProductTypes() {
        return productTypesService.getAllProductTypes();
    }

    @PostMapping("/productType")
    public List<Producttypes> saveProductTypes(@RequestBody List<Producttypes> productTypes) {
        return productTypesService.saveProductTypes(productTypes);
    }

    @PostMapping("/product")
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping(value = "/products/upload", consumes = { "multipart/form-data" })
    public String uploadProducts(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }
        return productService.uploadProducts(file);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        System.out.println("In get products....");
        return productService.getAllProducts();
    }

    @GetMapping("/products/{companyName}")
    public List<Product> getProductsByCompany(@PathVariable String companyName) {
        return productService.getProductsByCompany(companyName);
    }

    @PostMapping("/product/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody CartRequestDTO cartRequestDTO) {
        List<CartItems> cartItems = cartService.addToCart(cartRequestDTO.getProduct(), cartRequestDTO.getQuantity());
        double total = cartService.calculateCartTotal();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product added/updated successfully");
        response.put("cartItems", cartItems);
        response.put("cartTotal", total);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cartItems")
    public List<CartItems> getCartItems() {
        return cartService.getAllItems();
    }

    @DeleteMapping("/product/removeFromCart")
    public ResponseEntity<?> removeFromCart(@RequestBody CartRequestDTO cartRequestDTO) {
        List<CartItems> cartItems = cartService.removeFromCart(
                cartRequestDTO.getProduct().getProductName(),
                cartRequestDTO.getQuantity());
        double total = cartService.calculateCartTotal();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product removed/updated successfully");
        response.put("cartItems", cartItems);
        response.put("cartTotal", total);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<?> cartCheckout(@RequestBody CheckoutRequestDTO request) {

        // 1. Fetch cart data
        List<CartItems> cartItems = cartService.getAllItems();
        double total = cartService.calculateCartTotal();

        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        // 2. Generate invoice (mock)
        String invoiceNumber = "INV-" + System.currentTimeMillis();

        // 2.5 Resolve userId from username
        com.example.GSTechSecuritySystem.model.User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + request.getUsername()));
        Long userId = user.getId();

        // 3. Save order to database
        Order savedOrder = orderService.saveOrder(
                userId,
                request.getUsername(),
                request.getCustomerName(),
                request.getMobileNumber(),
                request.getAddress(),
                cartItems,
                total,
                "Confirmed");

        // 4. Build WhatsApp message
        String message = invoiceMessageBuilder.buildMessage(
                request.getCustomerName(),
                cartItems,
                total);

        // 5. Send WhatsApp message (mock number or real later)
        whatsAppSender.sendText(
                request.getMobileNumber(), // later: real WhatsApp number
                message);

        // 6. Clear cart after checkout
        cartService.clearCart();

        // 7. Response
        Map<String, Object> response = new HashMap<>();
        response.put("invoiceNumber", invoiceNumber);
        response.put("orderId", savedOrder.getOrderId());
        response.put("totalAmount", total);
        response.put("message", "Order placed successfully and invoice sent via WhatsApp");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{username}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUsername(@PathVariable("username") String username) {
        List<OrderResponseDTO> orders = orderService.getOrdersByUsername(username);
        return ResponseEntity.ok(orders);
    }

}
