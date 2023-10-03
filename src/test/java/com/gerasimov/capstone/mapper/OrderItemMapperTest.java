package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemMapperTest {

    private final OrderItemMapper orderItemMapper = Mappers.getMapper(OrderItemMapper.class);

    @Test
    void testToDtoWhenOrderItemNotNullThenReturnOrderItemDto() {

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setDishPrice(10.0);
        orderItem.setQuantity(2);

        OrderItemDto orderItemDto = orderItemMapper.toDto(orderItem);

        assertNotNull(orderItemDto);
        assertEquals(orderItemDto.getId(), orderItem.getId());
        assertEquals(orderItemDto.getDishPrice(), orderItem.getDishPrice());
        assertEquals(orderItemDto.getQuantity(), orderItem.getQuantity());
    }

    @Test
    void testToDtoWhenOrderItemNullThenReturnNull() {

        OrderItemDto orderItemDto = orderItemMapper.toDto(null);

        assertNull(orderItemDto);
    }
}