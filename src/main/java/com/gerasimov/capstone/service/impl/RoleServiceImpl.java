package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.repository.RoleRepository;
import com.gerasimov.capstone.service.RoleService;
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

    @Override
    public Role findCommonRole(){
        return roleRepository.findById(1L).orElseThrow(()-> new RestaurantException("Can't find common role in database"));
    }

    @Override
    public Role findByName(String roleName){
        return roleRepository.findByName(roleName).orElseThrow( () ->
                new RestaurantException(String.format("Can't find role with name %s", roleName)));
    }
}
