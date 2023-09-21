package com.gerasimov.capstone.domain;

import com.gerasimov.capstone.entity.Dish;
import com.gerasimov.capstone.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private OrderDto order;
    private DishDto dish;
    private Double dishPrice;
    private int quantity = 0;
}
