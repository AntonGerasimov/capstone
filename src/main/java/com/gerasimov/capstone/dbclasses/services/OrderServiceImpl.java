package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.Dish;
import com.gerasimov.capstone.dbclasses.domain.Order;
import com.gerasimov.capstone.dbclasses.entity.OrderEntity;
import com.gerasimov.capstone.dbclasses.mappers.DishMapper;
import com.gerasimov.capstone.dbclasses.mappers.OrderMapper;
import com.gerasimov.capstone.dbclasses.repositories.DishRepository;
import com.gerasimov.capstone.dbclasses.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }
    @Override
    public List<Order> getAllOrders(){
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<Order> orders = orderEntities.stream()
                .map(orderMapper::mapOrderEntityToDomain)
                .collect(Collectors.toList());
        return orders;
    }
}
