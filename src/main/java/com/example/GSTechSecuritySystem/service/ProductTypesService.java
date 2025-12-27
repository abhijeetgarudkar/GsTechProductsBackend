package com.example.GSTechSecuritySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GSTechSecuritySystem.model.Producttypes;
import com.example.GSTechSecuritySystem.repository.ProductTypeRepo;

@Service
public class ProductTypesService {

	@Autowired
	ProductTypeRepo productTypeRepo;

	public List<Producttypes> getAllProductTypes() {
		// TODO Auto-generated method stub
		return productTypeRepo.findAll();
	}

	public List<Producttypes> saveProductTypes(List<Producttypes> productTypes) {
		// TODO Auto-generated method stub
		return productTypeRepo.saveAll(productTypes);
	}

}
