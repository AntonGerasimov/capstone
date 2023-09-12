package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();
    List<Order> findByDeliveryAddressId(Long addressId);
    void delete(Long orderId);
}
