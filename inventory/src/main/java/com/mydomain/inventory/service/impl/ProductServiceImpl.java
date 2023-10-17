package com.mydomain.inventory.service.impl;

import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mydomain.inventory.model.entity.Product;
import com.mydomain.inventory.repository.ProductRepository;
import com.mydomain.inventory.service.core.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Product getProduct(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		Product product = productOptional.orElseThrow(() -> new NotFoundException("Product not found"));
		return product;
	}

	@Override
	@Transactional
	public String addProduct(Product product) {
		if(product == null) {
			throw new NullPointerException("Request cannot be null");
		}
		productRepository.save(product);
		return "Successfully added";
	}

}
