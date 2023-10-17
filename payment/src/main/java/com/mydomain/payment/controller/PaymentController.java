package com.mydomain.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydomain.payment.model.entity.Order;
import com.mydomain.payment.service.core.OrderService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	OrderService orderService;
	
	@PostMapping("/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody Order order) {
		String msg = orderService.placeOrder(order);
		if(msg.equalsIgnoreCase("Request cannot be null") || msg.equalsIgnoreCase("Product does not exist"))
			return new ResponseEntity<String>(msg,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
}
