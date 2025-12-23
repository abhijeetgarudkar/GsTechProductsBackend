package com.example.GSTechSecuritySystem.Do;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Producttypes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer productTypeId;
	String productType;


    public Producttypes() {
    }

    public Producttypes(int productTypeId, String productType) {
		this.productTypeId = productTypeId;
		this.productType = productType;
	}


	public int getProductTypeId() {
		return productTypeId;
	}


	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	@Override
	public String toString() {
		return "Producttypes [productTypeId=" + productTypeId + ", productType=" + productType + "]";
	}
	
	

}
