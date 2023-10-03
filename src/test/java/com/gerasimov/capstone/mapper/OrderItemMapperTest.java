package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemMapperTest {

    private final OrderItemMapper orderItemMapper = OrderItemMapper.INSTANCE;

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

    @Test
    void testToEntityWhenOrderItemDtoNotNullThenReturnOrderItem() {

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(1L);
        orderItemDto.setDishPrice(10.0);
        orderItemDto.setQuantity(2);

        OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);

        assertNotNull(orderItem);
        assertEquals(orderItem.getId(), orderItemDto.getId());
        assertEquals(orderItem.getDishPrice(), orderItemDto.getDishPrice());
        assertEquals(orderItem.getQuantity(), orderItemDto.getQuantity());
    }

    @Test
    void testToEntityWhenOrderItemDtoNullThenReturnNull() {

        OrderItem orderItem = orderItemMapper.toEntity(null);

        assertNull(orderItem);
    }
}