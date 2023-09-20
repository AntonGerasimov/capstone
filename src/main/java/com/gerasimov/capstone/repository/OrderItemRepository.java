package com.gerasimov.capstone.repository;

import com.gerasimov.capstone.entity.Dish;
import com.gerasimov.capstone.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByDish(Dish dish);
}
