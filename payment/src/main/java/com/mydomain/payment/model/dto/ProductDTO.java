package com.mydomain.payment.model.dto;

import javax.validation.constraints.NotNull;

public class ProductDTO {

	private Long id;
		
	@NotNull
	private String name;
	
	@NotNull
	private Double price;
	
	@NotNull
	private Long quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
}
