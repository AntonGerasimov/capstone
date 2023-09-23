package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.entity.OrderItem;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.DishMapper;
import com.gerasimov.capstone.mapper.OrderItemMapper;
import com.gerasimov.capstone.mapper.OrderMapper;
import com.gerasimov.capstone.repository.OrderItemRepository;
import com.gerasimov.capstone.service.OrderItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemRepository orderItemRepository;
    private OrderItemMapper orderItemMapper;
    private DishMapper dishMapper;
    private OrderMapper orderMapper;

    @Override
    public OrderItemDto findById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new RestaurantException(String.format("Can't find order item with id %d in database", id)));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public OrderItemDto findByDish(DishDto dishDto) {
        OrderItem orderItem = orderItemRepository.findByDish(dishMapper.toEntity(dishDto)).orElseThrow(() -> new RestaurantException(String.format("Can't find order item with dish %s in database", dishDto.toString())));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public List<OrderItemDto> findByOrder(OrderDto orderDto) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder(orderMapper.toEntity(orderDto));
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public void save(OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
        orderItemRepository.save(orderItem);
    }


}
