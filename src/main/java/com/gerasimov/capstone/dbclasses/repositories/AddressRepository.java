package com.gerasimov.capstone.dbclasses.repositories;

import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}
