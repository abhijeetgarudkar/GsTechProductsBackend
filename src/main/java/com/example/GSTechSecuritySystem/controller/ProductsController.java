package com.example.GSTechSecuritySystem.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.GSTechSecuritySystem.Do.*;
import com.example.GSTechSecuritySystem.service.AuthService;
import com.example.GSTechSecuritySystem.service.CartService;
import com.example.GSTechSecuritySystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.GSTechSecuritySystem.service.ProductTypesService;
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

	public ProductsController(ProductTypesService productTypesService,AuthService authService) {
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

    @PostMapping(value = "/products/upload", consumes = {"multipart/form-data"})
    public String uploadProducts(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }
        return productService.uploadProducts(file);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
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
}
