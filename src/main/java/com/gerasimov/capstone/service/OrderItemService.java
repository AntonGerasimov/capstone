package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderItemDto;

public interface OrderItemService {
    OrderItemDto findById(Long id);

    OrderItemDto findByDish(DishDto dishDto);
}
