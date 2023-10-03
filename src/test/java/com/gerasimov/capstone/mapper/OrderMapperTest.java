package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Order;
import com.gerasimov.capstone.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class OrderMapperTest {

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @Test
    void testToDtoWhenOrderEntityProvidedThenOrderDtoReturned() {

        Order order = new Order();
        order.setCustomer(new User());
        order.getCustomer().setFirstName("John");
        order.getCustomer().setLastName("Doe");

        OrderDto orderDto = orderMapper.toDto(order);

        assertEquals(orderDto.getCustomer().getFirstName(), order.getCustomer().getFirstName());
        assertEquals(orderDto.getCustomer().getLastName(), order.getCustomer().getLastName());
    }

    @Test
    void testToDtoWhenGivenNullOrderEntityThenReturnNull() {

        OrderDto orderDto = orderMapper.toDto(null);

        assertNull(orderDto);
    }

    @Test
    void testToEntityWhenOrderDtoProvidedThenOrderEntityReturned() {

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomer(new UserDto());
        orderDto.getCustomer().setFirstName("John");
        orderDto.getCustomer().setLastName("Doe");

        Order order = orderMapper.toEntity(orderDto);

        assertEquals(order.getCustomer().getFirstName(), orderDto.getCustomer().getFirstName());
        assertEquals(order.getCustomer().getLastName(), orderDto.getCustomer().getLastName());
    }

    @Test
    void testToEntityWhenGivenNullOrderDtoThenReturnNull() {

        Order order = orderMapper.toEntity(null);

        assertNull(order);
    }
}