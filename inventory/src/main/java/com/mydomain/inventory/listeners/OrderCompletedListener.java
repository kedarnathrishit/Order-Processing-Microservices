package com.mydomain.inventory.listeners;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydomain.inventory.model.entity.Product;
import com.mydomain.inventory.repository.ProductRepository;
import com.mydomain.payment.model.entity.Order;

@Component
public class OrderCompletedListener {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(OrderCompletedListener.class.getName());
	
	@Transactional
	@KafkaListener(topics = "order-completed-event", groupId = "inventory-group")
	public void listen(String orderInString) {
		ObjectMapper objectMapper = new ObjectMapper();
		Order order = null;
		try {
			order = objectMapper.readValue(orderInString, Order.class);
			Product product = productRepository.findById(order.getProductId()).get();
			if(product!=null && order.getQuantity()<=product.getQuantity()) {
				product.setQuantity(product.getQuantity()-order.getQuantity());
			}
			else {
				throw new Exception("Order quantity not sufficient");
			}
		} catch (Exception e) {
			logger.error("Order not confirmed",e);
			if(order!=null) {
				kafkaTemplate.send("order-cancelled-event",order.getId().toString());
			}
        }
	}
}
