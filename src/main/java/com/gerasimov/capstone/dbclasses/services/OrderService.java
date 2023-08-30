package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();
}
