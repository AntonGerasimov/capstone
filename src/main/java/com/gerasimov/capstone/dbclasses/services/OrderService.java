package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.OrderDto;
import com.gerasimov.capstone.dbclasses.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();
    void delete(Long orderId);
}
