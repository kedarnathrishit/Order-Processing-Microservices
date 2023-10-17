package com.mydomain.payment.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydomain.payment.model.dto.ProductDTO;
import com.mydomain.payment.model.entity.Order;
import com.mydomain.payment.repository.OrderRepository;
import com.mydomain.payment.service.core.OrderService;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Override
	@Transactional
	public String placeOrder(Order order) {
		if(order == null) {
			return "Request cannot be null";
		}
		String getProductUrl = "http://127.0.0.1:8071/products/getProductById/"+order.getProductId();
		ProductDTO productDTO = restTemplate.getForObject(getProductUrl,ProductDTO.class);
		if(productDTO == null) {
			return "Product does not exist";
		}
		order.setFee(0.2*productDTO.getPrice());
		order.setTotal(1.2*productDTO.getPrice());
		order.setStatus("Completed");
		orderRepository.save(order);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String orderInString = objectMapper.writeValueAsString(order);
			kafkaTemplate.send("order-completed-event",orderInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "Order Placed Successfully";
	}

}
