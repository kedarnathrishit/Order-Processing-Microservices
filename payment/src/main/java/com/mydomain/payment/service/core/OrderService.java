package com.mydomain.payment.service.core;

import com.mydomain.payment.model.entity.Order;

public interface OrderService {

	public String placeOrder(Order order);
}
