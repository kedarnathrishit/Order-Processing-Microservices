package com.mydomain.inventory.service.core;

import com.mydomain.inventory.model.entity.Product;

public interface ProductService {

	Product getProduct(Long id);
	
	String addProduct(Product product);
}
