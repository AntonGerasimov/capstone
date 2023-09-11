package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long id);
    UserDto findByUsername(String username);

    UserDto save(UserDto user);


    UserDto update(Long id, UserDto user);

    UserDto updateByUsername(String username, UserDto user);

    String delete(Long userId); //returns the redirected page

    boolean emailExists(String email);
}