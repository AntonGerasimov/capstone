package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.UserDto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDto> findAll();

    UserDto findAuthenticatedUser();

    UserDto findById(Long id);

    UserDto findByUsername(String username);

    void login(UserDto newUser, HttpServletRequest request);

    UserDto save(UserDto user);

    UserDto prepareEdit(Long editId);

    void update(UserDto user);

    void delete(Long userId, HttpServletRequest request, HttpServletResponse response, Authentication authentication);

    boolean emailExists(String email);

    String findRedirectPageAfterEdit(Long id);

    String findRedirectPageAfterDelete(Long id);
}