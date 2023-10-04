package com.gerasimov.capstone.specification;

import com.gerasimov.capstone.entity.Order;
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
public class OrderSpecifications implements Specification<Order> {

    private User customer;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate customerPredicate = (customer != null) ? cb.equal(root.get("customer"), customer) : cb.conjunction();
        Predicate datePredicate = cb.between(root.get("created"), startDateTime, endDateTime);
        return cb.and(customerPredicate, datePredicate);
    }
}
