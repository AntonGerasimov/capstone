package com.gerasimov.capstone.repository;

import com.gerasimov.capstone.entity.Order;
import com.gerasimov.capstone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(User user);
    List<Order> findByDeliveryAddressId(Long deliveryAddressId);
}
