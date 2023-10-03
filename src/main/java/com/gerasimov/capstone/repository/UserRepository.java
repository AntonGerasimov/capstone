package com.gerasimov.capstone.repository;

import com.gerasimov.capstone.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    void deleteById(Long id);
}
