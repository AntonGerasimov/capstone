package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.entity.Order;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.OrderMapper;
import com.gerasimov.capstone.repository.OrderRepository;
import com.gerasimov.capstone.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> findAll(){
        List<Order> orderEntities = orderRepository.findAll();
        return orderEntities.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto findById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(String.format("Can't find order with id %d in database", id)));
        return orderMapper.toDto(order);
    }

    @Override
    public List<Order> findByDeliveryAddressId(Long addressId){
        return orderRepository.findByDeliveryAddressId(addressId);
    }

    @Override
    @Transactional
    public void delete(Long orderId){
        OrderDto orderDto = findById(orderId);
        orderDto.setActive(false);
        orderRepository.save(orderMapper.toEntity(orderDto));
        log.info("Delete order with id " + orderDto.getId());
    }
}
