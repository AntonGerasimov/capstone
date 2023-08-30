package com.gerasimov.capstone.dbclasses.mappers;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.domain.OrderDto;
import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.entity.Order;
import com.gerasimov.capstone.dbclasses.entity.User;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import com.gerasimov.capstone.dbclasses.repositories.OrderRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public OrderMapper(OrderRepository orderRepository, UserRepository userRepository, AddressRepository addressRepository, UserMapper userMapper, AddressMapper addressMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
    }

    public OrderDto mapOrderEntityToDomain(Order orderEntity) {
        Long id = orderEntity.getOrderId();
        Long customerId = orderEntity.getCustomerId();
        User customerEntity = userRepository.findById(customerId).orElse(null);
        UserDto customer = userMapper.mapUserEntityToDomain(customerEntity);
        LocalDateTime orderDateTime = orderEntity.getOrderDatetime();
        String status = orderEntity.getOrderStatus();
        Long addressId = orderEntity.getDeliveryAddressId();
        Address addressEntity = addressRepository.getById(addressId);
        AddressDto address = addressMapper.mapAddressEntityToDomain(addressEntity);
        return new OrderDto(id, customer, orderDateTime, status, address);
    }
}
