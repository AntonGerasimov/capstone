package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.UserDto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto findByUsername(String username);
    void login(UserDto newUser, HttpServletRequest request);

    UserDto save(UserDto user);

    UserDto prepareEdit(Long editId, Authentication authentication);

    void update(Long id, UserDto user);

    void delete(Long userId, HttpServletRequest request, HttpServletResponse response, Authentication authentication);

    boolean emailExists(String email);
    String findRedirectPageAfterEdit(Long id, Authentication authentication);
    String findRedirectPageAfterDelete(Long id, Authentication authentication);
    UserDto findAuthenticatedUser(Authentication authentication);
}