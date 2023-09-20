package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.OrderItemDto;

import java.util.List;

public interface OrderItemService {
    OrderItemDto findById(Long id);
    OrderItemDto findByDish(DishDto dishDto);
    List<OrderItemDto> findByOrder(OrderDto orderDto);
    void save(OrderItemDto orderItemDto);
}
