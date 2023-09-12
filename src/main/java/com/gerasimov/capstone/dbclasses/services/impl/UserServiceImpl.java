package com.gerasimov.capstone.dbclasses.services.impl;


import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.entity.Role;
import com.gerasimov.capstone.dbclasses.entity.User;
import com.gerasimov.capstone.dbclasses.mappers.UserMapper;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import com.gerasimov.capstone.dbclasses.repositories.OrderRepository;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import com.gerasimov.capstone.dbclasses.services.AddressService;
import com.gerasimov.capstone.dbclasses.services.UserService;
import com.gerasimov.capstone.exceptions.RestaurantException;
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
    private RoleRepository roleRepository;
    private AddressRepository addressRepository;
    private OrderRepository orderRepository;
    private AddressService addressService;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto findById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return userMapper.toDto(user.get());
        }else{
            return null;
        }
    }

    public UserDto findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            return userMapper.toDto(user.get());
        }else{
            return null;
        }
    }

    @Override
    public UserDto save(UserDto userDto) {
        if (emailExists(userDto.getEmail())){
            throw new RestaurantException("Duplicate user with email: " + userDto.getEmail());
        }
        Role role = roleRepository.findById(1L).orElse(null);
        userDto.setRole(role);
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
        return userDto;
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto){
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
            return userDto;
        } else{
            return null;
        }
    }

    @Override
    @Transactional
    public UserDto updateByUsername(String username, UserDto userDto){
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
            return userDto;
        } else{
            return null;
        }
    }

    @Override
    @Transactional
    public String delete(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            log.info("Delete user with username " + user.getUsername() + ". (Make isActive = false)");
            user.setActive(false);
        }
        return null;
    }

    @Override
    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

}