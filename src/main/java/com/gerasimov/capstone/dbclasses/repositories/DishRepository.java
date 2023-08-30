package com.gerasimov.capstone.dbclasses.repositories;

import com.gerasimov.capstone.dbclasses.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
