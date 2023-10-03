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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private OrderItem orderItem;
    private OrderItemDto orderItemDto;

    @BeforeEach
    public void setUp() {
        orderItem = new OrderItem();
        orderItemDto = new OrderItemDto();
    }

    @Test
    void testFindByIdWhenOrderItemExistsThenReturnOrderItemDto() {
        Long id = 1L;
        when(orderItemRepository.findById(id)).thenReturn(Optional.of(orderItem));
        when(orderItemMapper.toDto(orderItem)).thenReturn(orderItemDto);

        OrderItemDto result = orderItemService.findById(id);

        assertEquals(orderItemDto, result);
    }

    @Test
    void testFindByIdWhenOrderItemDoesNotExistThenThrowException() {
        Long id = 1L;
        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RestaurantException.class, () -> orderItemService.findById(id));
    }
}