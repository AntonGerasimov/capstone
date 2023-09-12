package com.gerasimov.capstone.service.impl;


import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.entity.User;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.UserRepository;
import com.gerasimov.capstone.service.RoleService;
import com.gerasimov.capstone.service.UserService;
import com.gerasimov.capstone.exception.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public Optional<UserDto> findById(Long id){
        Optional<User> user = userRepository.findById(id);
        return Optional.of(userMapper.toDto(user.get()));
    }
    @Override
    public Optional<UserDto> findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return Optional.of(userMapper.toDto(user.get()));
    }

    @Override
    public Optional<UserDto> save(UserDto userDto) {
        if (emailExists(userDto.getEmail())){
            throw new RestaurantException("Duplicate user with email: " + userDto.getEmail());
        }
        Role commonRole = roleService.findCommonRole();
        userDto.setRole(commonRole); // set role to default --- common
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
        return Optional.of(userDto);
    }

    @Override
    @Transactional
    public Optional<UserDto> update(Long id, UserDto userDto){
        log.info("Updating user with id " + id);
        User userToUpdate = userRepository.findById(id).orElse(null);
        if (userToUpdate != null){
            if (!userToUpdate.getFirstName().equals(userDto.getFirstName())){
                userToUpdate.setFirstName(userDto.getFirstName());
            }
            if (!userToUpdate.getLastName().equals(userDto.getLastName())){
                userToUpdate.setLastName(userDto.getLastName());
            }
            if (!userToUpdate.getRole().equals(userDto.getRole())){
                userToUpdate.setRole(userDto.getRole());
            }
            return Optional.of(userDto);
        } else{
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<UserDto> updateByUsername(String username, UserDto userDto){
        log.info("Updating user with username " + username);
        User userToUpdate = userRepository.findByUsername(username).orElse(null);
        log.info("User to update: " + userToUpdate.getFirstName() + " " + userToUpdate.getLastName());
        log.info("Updated User info: " + userDto.getFirstName() + " " + userDto.getLastName());
        if (userToUpdate != null){
            if (!userToUpdate.getFirstName().equals(userDto.getFirstName())){
                userToUpdate.setFirstName(userDto.getFirstName());
            }
            if (!userToUpdate.getLastName().equals(userDto.getLastName())){
                userToUpdate.setLastName(userDto.getLastName());
            }
            return Optional.of(userDto);
        } else{
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (user.getId() != 1L){
                log.info("Delete user with username " + user.getUsername() + ". (Make isActive = false)");
                user.setActive(false);
            }else{
                throw new RestaurantException("Can't delete main admin!");
            }
        } else{
            throw new RestaurantException("User that you want to delete doesn't exist");
        }
    }

    @Override
    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

}