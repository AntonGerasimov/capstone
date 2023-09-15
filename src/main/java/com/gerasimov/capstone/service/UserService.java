package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();

    Optional<UserDto> findById(Long id);

    Optional<UserDto> findByUsername(String username);

    Optional<UserDto> save(UserDto user);

    void prepareEdit(Long editId, Authentication authentication, Model model);

    void update(Long id, UserDto user);

    void delete(Long userId, HttpServletRequest request, HttpServletResponse response, Authentication authentication);

    boolean emailExists(String email);
    String findRedirectPageAfterEdit(Long id, Authentication authentication);
    String findRedirectPageAfterDelete(Long id, Authentication authentication);
    UserDto findAuthenticatedUser(Authentication authentication);
}