package com.example.GSTechSecuritySystem.Do;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int productId;
    @Column(name = "company_name")
    String companyName;
    @Column(name = "product_name")
	String productName;
    @Column(name = "product_price")
	double productPrice;

    public Product() {
    }

    public Product(String companyName, String productName, double productPrice) {
        this.companyName = companyName;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", companyName='" + companyName + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }
}
