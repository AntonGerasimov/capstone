package com.gerasimov.capstone.repository;

import com.gerasimov.capstone.entity.Order;
import com.gerasimov.capstone.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);
    List<Order> findByCustomer(User user);
    List<Order> findByDeliveryAddressId(Long deliveryAddressId);
}
