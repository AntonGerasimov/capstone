package com.gerasimov.capstone.dbclasses.services;


import com.gerasimov.capstone.dbclasses.domain.User;
import com.gerasimov.capstone.dbclasses.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void saveUser(User user);
}
