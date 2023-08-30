package com.gerasimov.capstone.dbclasses.services;


import com.gerasimov.capstone.dbclasses.domain.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    void saveUser(UserDto user);
}
