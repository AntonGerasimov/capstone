package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    Page<UserDto> findAllByRoles(boolean commonRole, boolean managerRole, boolean adminRole, Pageable pageable);

    Page<UserDto> findByFilter(List<Role> roles, Pageable pageable);

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