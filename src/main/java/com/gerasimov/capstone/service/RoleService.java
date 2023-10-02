package com.gerasimov.capstone.service;

import com.gerasimov.capstone.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findCommonRole();

    Role findByName(String roleName);
}
