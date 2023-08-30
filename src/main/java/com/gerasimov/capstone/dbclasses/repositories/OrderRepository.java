package com.gerasimov.capstone.dbclasses.repositories;

import com.gerasimov.capstone.dbclasses.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
