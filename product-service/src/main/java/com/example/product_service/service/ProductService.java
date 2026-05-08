package com.example.product_service.service;

import org.springframework.stereotype.Service;

import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;

@Service
public class ProductService {

	private  ProductRepository productRepo;

	public ProductService(ProductRepository productRepo) {
		super();
		this.productRepo = productRepo;
	}
	  
	public Product saveProduct(Product product) {	
		return productRepo.save(product);		
	} 
	
	public Product getProductById(Long id) {
		return productRepo.findById(id).orElse(null);
		
	}
	  
}
