package com.gerasimov.capstone.specification;

import com.gerasimov.capstone.entity.Dish;
import com.gerasimov.capstone.entity.User;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

@Component
@NoArgsConstructor
@Setter
public class DishSpecifications implements Specification<Dish> {

    private String category;
    private boolean isAvailable;
    private double minPrice;
    private double maxPrice;


    @Override
    public Predicate toPredicate(Root<Dish> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate categoryPredicate = cb.equal(root.get("category"), category);
        Predicate availablePredicate = cb.equal(root.get("isAvailable"), isAvailable);
        Predicate pricePredicate = cb.between(root.get("price"), minPrice, maxPrice);

        return cb.and(categoryPredicate, availablePredicate, pricePredicate);

    }
}
