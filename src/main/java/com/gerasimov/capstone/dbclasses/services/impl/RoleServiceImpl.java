package com.gerasimov.capstone.dbclasses.services.impl;

import com.gerasimov.capstone.dbclasses.entity.Role;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    @Override
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
