package com.gerasimov.capstone.specification;

import com.gerasimov.capstone.entity.Dish;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
@NoArgsConstructor
@Setter
public class DishSpecifications implements Specification<Dish> {

    private String category;
    private Boolean isAvailable;
    private double minPrice;
    private double maxPrice;


    @Override
    public Predicate toPredicate(Root<Dish> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate categoryPredicate = (category != null) ? cb.equal(root.get("category"), category) : cb.conjunction();
        Predicate availablePredicate = (isAvailable != null) ? cb.equal(root.get("isAvailable"), isAvailable) : cb.conjunction();
        Predicate pricePredicate = cb.between(root.get("price"), minPrice, maxPrice);

        return cb.and(categoryPredicate, availablePredicate, pricePredicate);

    }

    public void setAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
