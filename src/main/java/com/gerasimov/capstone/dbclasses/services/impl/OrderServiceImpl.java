package com.gerasimov.capstone.dbclasses.services.impl;

import com.gerasimov.capstone.dbclasses.domain.OrderDto;
import com.gerasimov.capstone.dbclasses.entity.Order;
import com.gerasimov.capstone.dbclasses.mappers.OrderMapper;
import com.gerasimov.capstone.dbclasses.repositories.OrderRepository;
import com.gerasimov.capstone.dbclasses.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> findAll(){
        List<Order> orderEntities = orderRepository.findAll();
        List<OrderDto> orders = orderEntities.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return orders;
    }

    @Override
    public void delete(Long orderId){
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null){
            log.info("Delete order with id " + order.getId());
            order.setCustomer(null);
            order.setDeliveryAddress(null);
            orderRepository.deleteById(orderId);
        }
    }
}
