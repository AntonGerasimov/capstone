package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Order;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.OrderMapper;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.OrderRepository;
import com.gerasimov.capstone.service.OrderItemService;
import com.gerasimov.capstone.service.OrderService;
import com.gerasimov.capstone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private UserService userService;
    private UserMapper userMapper;
    private OrderItemService orderItemService;

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
    public List<OrderDto> findByCustomer(UserDto userDto){
        List<Order> orders = orderRepository.findByCustomer(userMapper.toEntity(userDto) );
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemDto> findOrderItems(OrderDto orderDto){
        return orderItemService.findByOrder(orderDto);
    }

    @Override
    public OrderDto save(OrderDto orderDto){
        setAuthenticatedUser(orderDto);
        setCurrentTime(orderDto);
        setDefaultStatus(orderDto);
        setActive(orderDto);

        Order order = orderMapper.toEntity(orderDto);
        OrderDto savedOrderDto = orderMapper.toDto(orderRepository.save(order));
        log.info(String.format("Order was created: %s", savedOrderDto.toString()));
        return savedOrderDto;
    }


    @Override
    @Transactional
    public void delete(Long orderId){
        OrderDto orderDto = findById(orderId);
        orderDto.setActive(false);
        orderRepository.save(orderMapper.toEntity(orderDto));
        log.info("Delete order with id " + orderDto.getId());
    }

    private void setAuthenticatedUser(OrderDto orderDto){
        UserDto customer = userService.findAuthenticatedUser();
        orderDto.setCustomer(customer);
    }

    private void setCurrentTime(OrderDto orderDto){
        orderDto.setCreated(LocalDateTime.now());
    }
    private void setDefaultStatus(OrderDto orderDto){
        orderDto.setStatus("Preparing");
    }

    private void setActive(OrderDto orderDto){
        orderDto.setActive(true);
    }



}
