package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class OrderItemServiceImpl {
    private OrderItemRepository orderItemRepository;
}
