package com.gerasimov.capstone.specification;

import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.entity.User;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;

@Component
@NoArgsConstructor
@Setter
public class UserSpecifications implements Specification<User> {

    private List<Role> roles;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.get("role")).value(roles);
    }

}
