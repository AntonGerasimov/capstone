package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
}
