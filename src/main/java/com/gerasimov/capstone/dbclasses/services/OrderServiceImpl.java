package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.OrderDto;
import com.gerasimov.capstone.dbclasses.entity.Order;
import com.gerasimov.capstone.dbclasses.mappers.OrderMapper;
import com.gerasimov.capstone.dbclasses.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> getAllOrders(){
        List<Order> orderEntities = orderRepository.findAll();
        List<OrderDto> orders = orderEntities.stream()
                .map(orderMapper::mapOrderEntityToDomain)
                .collect(Collectors.toList());
        return orders;
    }
}
