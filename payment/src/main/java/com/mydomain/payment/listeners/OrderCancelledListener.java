package com.mydomain.payment.listeners;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mydomain.payment.model.entity.Order;
import com.mydomain.payment.repository.OrderRepository;

@Component
public class OrderCancelledListener {

	@Autowired
	OrderRepository orderRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(OrderCancelledListener.class.getName());
	
	@Transactional
	@KafkaListener(topics = "order-cancelled-event", groupId = "payment-group")
	public void listen(String orderId) {
		Order order = orderRepository.findById(Long.valueOf(orderId)).get();
		if(order!=null) {
			try {
			    Thread.sleep(2000); //Refunding payment
			} catch (InterruptedException e) {
			    logger.error("issue while refunding",e);
			}
			order.setStatus("Cancelled");
		}
	}
}
