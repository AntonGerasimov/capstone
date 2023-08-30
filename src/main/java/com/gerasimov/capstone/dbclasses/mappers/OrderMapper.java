package com.gerasimov.capstone.dbclasses.mappers;

import com.gerasimov.capstone.dbclasses.domain.Address;
import com.gerasimov.capstone.dbclasses.domain.FullAddress;
import com.gerasimov.capstone.dbclasses.domain.Order;
import com.gerasimov.capstone.dbclasses.domain.User;
import com.gerasimov.capstone.dbclasses.entity.AddressEntity;
import com.gerasimov.capstone.dbclasses.entity.OrderEntity;
import com.gerasimov.capstone.dbclasses.entity.UserEntity;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import com.gerasimov.capstone.dbclasses.repositories.DishRepository;
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

    public Order mapOrderEntityToDomain(OrderEntity orderEntity) {
        Long id = orderEntity.getOrderId();
        Long customerId = orderEntity.getCustomerId();
        UserEntity customerEntity = userRepository.findById(customerId).orElse(null);
        User customer = userMapper.mapUserEntityToDomain(customerEntity);
        LocalDateTime orderDateTime = orderEntity.getOrderDatetime();
        String status = orderEntity.getOrderStatus();
        Long addressId = orderEntity.getDeliveryAddressId();
        AddressEntity addressEntity = addressRepository.getById(addressId);
        Address address = addressMapper.mapAddressEntityToDomain(addressEntity);
        return new Order(id, customer, orderDateTime, status, address);
    }
}
