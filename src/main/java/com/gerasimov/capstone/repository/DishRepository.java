package com.gerasimov.capstone.repository;

import com.gerasimov.capstone.entity.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long>, JpaSpecificationExecutor<Dish> {

    Page<Dish> findAll(Specification<Dish> spec, Pageable pageable);

    List<Dish> findByIsAvailableTrue();
}
