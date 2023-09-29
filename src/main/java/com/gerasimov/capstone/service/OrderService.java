package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> findAll();

    OrderDto findById(Long id);

    List<Order> findByDeliveryAddressId(Long addressId);

    List<OrderDto> findByCustomer(UserDto userDto);

    List<OrderItemDto> findOrderItems(OrderDto orderDto);

    OrderDto save(OrderDto orderDto);

    void delete(Long orderId);

    Page<OrderDto> getOrdersForAuthenticatedUserPageable(Pageable pageable, LocalDate startDate, LocalDate endDate);

    Map<Long, Double> getTotalPrices();
    double calcTotalPrice(OrderDto orderDto);
}
