package com.gerasimov.capstone.dbclasses.services;


import com.gerasimov.capstone.dbclasses.domain.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto save(UserDto user);

    void makeLogin(UserDto newUser);

    UserDto update(Long id, UserDto user);

    void delete(Long userId);

    boolean emailExists(String email);
}