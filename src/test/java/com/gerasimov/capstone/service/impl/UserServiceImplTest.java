package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.entity.User;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.UserRepository;
import com.gerasimov.capstone.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findIdTest(){
        Long userId = 2L;

        User user = new User();
        user.setId(2L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@gmail.com");
        user.setUsername("john");
        user.setPassword("$2a$12$WocppmSlhS5v.r8CN4SSUeJuCdwh6ZhQMOANEn.jh/knDCn4xy.Bi");
        user.setRole(new Role(2L, "ROLE_manager"));
        user.setActive(true);

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(2L);
        expectedUserDto.setFirstName("John");
        expectedUserDto.setLastName("Doe");
        expectedUserDto.setEmail("john@gmail.com");
        expectedUserDto.setUsername("john");
        expectedUserDto.setPassword("$2a$12$WocppmSlhS5v.r8CN4SSUeJuCdwh6ZhQMOANEn.jh/knDCn4xy.Bi");
        expectedUserDto.setRole(new Role(2L, "ROLE_manager"));
        expectedUserDto.setActive(true);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(userRepository.findById(userId).get())).thenReturn(expectedUserDto);

        UserDto actualUserDto = userService.findById(userId);

        log.info(userMapper.toDto(userRepository.findById(userId).get()).toString());

//        assertEquals(userRepository.findById(2L), Optional.of(user));

        assertEquals(expectedUserDto, actualUserDto);

    }


}