package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();

    Optional<UserDto> findById(Long id);

    Optional<UserDto> findByUsername(String username);

    Optional<UserDto> save(UserDto user);

    Optional<UserDto> update(Long id, UserDto user);

    Optional<UserDto> updateByUsername(String username, UserDto user);

    void delete(Long userId); //returns the redirected page

    boolean emailExists(String email);
}