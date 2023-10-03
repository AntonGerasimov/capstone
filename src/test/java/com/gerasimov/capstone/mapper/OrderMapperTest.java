package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.entity.Order;
import com.gerasimov.capstone.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class OrderMapperTest {

    private OrderMapper orderMapper = OrderMapper.INSTANCE;

    @Test
    void testToDtoWhenOrderEntityProvidedThenOrderDtoReturned() {

        Order order = new Order();
        order.setCustomer(new User());
        order.getCustomer().setFirstName("John");
        order.getCustomer().setLastName("Doe");

        OrderDto orderDto = orderMapper.toDto(order);

        assertEquals(orderDto.getCustomer().getFirstName(), order.getCustomer().getFirstName());
    }

    @Test
    void testToDtoWhenGivenNullOrderEntityThenReturnNull() {

        OrderDto orderDto = orderMapper.toDto(null);

        assertNull(orderDto);
    }


}