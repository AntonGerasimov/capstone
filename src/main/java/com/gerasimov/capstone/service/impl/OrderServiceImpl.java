package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.entity.Order;
import com.gerasimov.capstone.mapper.OrderMapper;
import com.gerasimov.capstone.repository.OrderRepository;
import com.gerasimov.capstone.service.OrderService;
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
    public List<Order> findByDeliveryAddressId(Long addressId){
        return orderRepository.findByDeliveryAddressId(addressId);
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
