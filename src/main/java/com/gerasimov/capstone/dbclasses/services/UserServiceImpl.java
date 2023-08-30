package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.User;
import com.gerasimov.capstone.dbclasses.mappers.UserMapper;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAllUsers(){
        List<User> userEntities = userRepository.findAll();
        List<UserDto> users = userEntities.stream()
                .map(userMapper::mapUserEntityToDomain)
                .collect(Collectors.toList());
        return users;
    }

    @Override
    public void saveUser(UserDto user){
        User userEntity = userMapper.mapUserDomainToEntity(user);
        userRepository.save(userEntity);
    }
}
