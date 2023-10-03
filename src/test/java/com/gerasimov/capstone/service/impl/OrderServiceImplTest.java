package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Order;
import com.gerasimov.capstone.mapper.OrderMapper;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.OrderRepository;
import com.gerasimov.capstone.service.UserService;
import com.gerasimov.capstone.specification.OrderSpecifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private OrderDto orderDto;

    @BeforeEach
    public void setUp() {
        order = new Order();
        orderDto = new OrderDto();
    }

    @Test
    void testFindAllWhenOrdersExistThenReturnOrderDtoList() {
        List<Order> orders = List.of(order);
        List<OrderDto> orderDtos = List.of(orderDto);

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        List<OrderDto> result = orderService.findAll();

        assertEquals(orderDtos, result);
    }

    @Test
    void testFindAllWhenNoOrdersThenReturnEmptyList() {
        List<Order> orders = Collections.emptyList();
        List<OrderDto> orderDtos = Collections.emptyList();

        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDto> result = orderService.findAll();

        assertEquals(orderDtos, result);
    }

    @Test
    void testGetOrdersForAuthenticatedUserPageableWhenOrdersExistThenReturnPageWithOrderDtos() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> ordersPage = new PageImpl<>(List.of(order), pageable, 1);
        Page<OrderDto> orderDtosPage = new PageImpl<>(List.of(orderDto), pageable, 1);

        when(orderRepository.findAll(any(OrderSpecifications.class), any(Pageable.class))).thenReturn(ordersPage);
        when(orderMapper.toDto(order)).thenReturn(orderDto);
        when(userService.findAuthenticatedUser()).thenReturn(new UserDto());

        Page<OrderDto> result = orderService.getOrdersForAuthenticatedUserPageable(startDate, endDate, pageable);

        assertEquals(orderDtosPage, result);
    }

    @Test
    void testGetOrdersForAuthenticatedUserPageableWhenNoOrdersThenReturnEmptyPage() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> ordersPage = Page.empty(pageable);
        Page<OrderDto> orderDtosPage = Page.empty(pageable);

        when(orderRepository.findAll(any(OrderSpecifications.class), any(Pageable.class))).thenReturn(ordersPage);

        Page<OrderDto> result = orderService.getOrdersForAuthenticatedUserPageable(startDate, endDate, pageable);

        assertEquals(orderDtosPage, result);
    }
}