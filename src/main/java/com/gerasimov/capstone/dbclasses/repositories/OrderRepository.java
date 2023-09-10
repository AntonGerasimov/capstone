package com.gerasimov.capstone.dbclasses.repositories;

import com.gerasimov.capstone.dbclasses.entity.Order;
import com.gerasimov.capstone.dbclasses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(User user);
    List<Order> findByDeliveryAddressId(Long deliveryAddressId);
}
