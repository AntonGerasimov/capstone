package com.gerasimov.capstone.dbclasses.services;


import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.User;
import com.gerasimov.capstone.dbclasses.mappers.UserMapper;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
        return usersDto;
    }

    public void saveUser(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        userRepository.save(user);
    }
}