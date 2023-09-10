package com.gerasimov.capstone.dbclasses.repositories;

import com.gerasimov.capstone.dbclasses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByUsername(String username);
    void deleteById(Long id);
}
