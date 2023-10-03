package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.entity.User;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.UserRepository;
import com.gerasimov.capstone.service.UserService;
import com.gerasimov.capstone.specification.UserSpecifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private UserMapper userMapper;

    @Test
    void testFindAllByRolesWhenUserExistsThenUserDtoIsReturned() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        Page<User> userPage = new PageImpl<>(users);

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(new UserDto());
        Page<UserDto> expectedUserDtoPage = new PageImpl<>(userDtos);

        when(userRepository.findAll(any(UserSpecifications.class), any(Pageable.class))).thenReturn(userPage);
        when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());

        Page<UserDto> actualUserDtoPage = userService.findAllByRoles(true, true, true, PageRequest.of(0, 1));

        assertEquals(expectedUserDtoPage.getContent().size(), actualUserDtoPage.getContent().size());
    }

    @Test
    void testFindAllByRolesWhenNoUserExistsThenEmptyPageIsReturned() {
        Page<User> userPage = Page.empty();

        when(userRepository.findAll(any(UserSpecifications.class), any(Pageable.class))).thenReturn(userPage);

        Page<UserDto> actualUserDtoPage = userService.findAllByRoles(true, true, true, PageRequest.of(0, 1));

        assertEquals(0, actualUserDtoPage.getContent().size());
    }

    @Test
    void testFindByIdWhenUserExistsTestThenUserDtoIsReturned() {
        User user = new User();
        UserDto expectedUserDto = new UserDto();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toDto(any())).thenReturn(expectedUserDto);

        UserDto actualUserDto = userService.findById(1L);

        assertEquals(expectedUserDto, actualUserDto);
    }
}