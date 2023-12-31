package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.entity.User;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.UserRepository;
import com.gerasimov.capstone.security.UserDetailsImpl;
import com.gerasimov.capstone.service.RoleService;
import com.gerasimov.capstone.service.UserService;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.specification.UserSpecifications;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;


    @Override
    public Page<UserDto> findAllByRoles(boolean isCommonPresent, boolean isManagerPresent, boolean isAdminPresent, Pageable pageable) {
        List<Role> roles = findRolesList(isCommonPresent, isManagerPresent, isAdminPresent);
        return findByFilter(roles, pageable);
    }

    @Override
    public Page<UserDto> findByFilter(List<Role> roles, Pageable pageable) {
        UserSpecifications userSpecifications = new UserSpecifications();
        userSpecifications.setRoles(roles);
        Page<User> usersPage = userRepository.findAll(userSpecifications, pageable);

        List<UserDto> userDtoList = usersPage
                .getContent()
                .stream()
                .map(userMapper::toDto)
                .toList();

        return new PageImpl<>(userDtoList, pageable, usersPage.getTotalElements());
    }

    @Override
    public UserDto findAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && (authentication.isAuthenticated())) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return findByUsername(userDetails.getUsername());
        } else {
            throw new RestaurantException("Can't find authenticated user. Please, sign in");
        }
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RestaurantException(String.format("Can't find user with id %d in database", id)));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RestaurantException(String.format("Can't find user with username %s in database", username)));
        return userMapper.toDto(user);
    }

    @Override
    public void login(UserDto newUser, HttpServletRequest request) {
        log.info(String.format("Initiate login for username %s", newUser.getUsername()));
        UserDto dbUser = findByUsername(newUser.getUsername());
        if (dbUser.isActive()) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(String.format("Successful login of username %s", newUser.getUsername()));
        } else {
            log.error(String.format("Can't find user with username %s", newUser.getUsername()));
            throw new RestaurantException(String.format("Can't find user with username %s", newUser.getUsername()));
        }
    }

    @Override
    public UserDto save(UserDto userDto) {
        validateEmail(userDto);
        setDefaultRole(userDto);
        encodeUserPassword(userDto);

        User user = userMapper.toEntity(userDto);
        UserDto savedUser = userMapper.toDto(userRepository.save(user));
        log.info(String.format("User %s was created", savedUser.toString()));
        return savedUser;
    }

    @Override
    public UserDto prepareEdit(Long editId) {
        if (isAuthenticatedUserAdmin() || isTheSameUser(editId)) {
            return findById(editId);
        } else {
            log.error(String.format("User doesn't have permission to edit user with id %s", editId));
            throw new RestaurantException("Sorry, you don't have access to edit this user");
        }
    }

    @Override
    @Transactional
    public void update(UserDto updatedUser) {
        log.info("Updating user " + updatedUser.toString());
        userRepository.save(userMapper.toEntity(updatedUser));
    }


    @Override
    @Transactional
    public void delete(Long deleteId, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        if (!hasPermissionToDelete(deleteId)) {
            log.error(String.format("User doesn't have permission to edit user with id %s", deleteId));
            throw new RestaurantException("Sorry, you don't have access to edit this user");
        }

        if (isTheSameUser(deleteId)) { // User wants to delete itself ---> first need to logout
            logoutCurrentUser(request, response, authentication);
        }

        deactivateUser(deleteId);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String findRedirectPageAfterEdit(Long id) { //suggests that user don't edit himself on /users page
        if (isTheSameUser(id)) {
            return String.format("redirect:/users/%d/personal-account", id);
        } else {
            return "redirect:/users";
        }
    }

    @Override
    public String findRedirectPageAfterDelete(Long id) { //suggests that user don't delete himself on /users page
        if (isTheSameUser(id)) {
            return "redirect:/";
        } else {
            return "redirect:/users";
        }
    }

    private List<Role> findRolesList(boolean common, boolean manager, boolean admin) {
        List<Role> rolesList = new ArrayList<>();
        addRoleToList(rolesList, common, "ROLE_common");
        addRoleToList(rolesList, manager, "ROLE_manager");
        addRoleToList(rolesList, admin, "ROLE_admin");
        return rolesList;
    }

    private void addRoleToList(List<Role> rolesList, boolean isPresent, String roleName) {
        if (isPresent) {
            Role role = roleService.findByName(roleName);
            rolesList.add(role);
        }
    }

    private void validateEmail(UserDto userDto) {
        if (emailExists(userDto.getEmail())) {
            log.error(String.format("User with email %s already exists", userDto.getEmail()));
            throw new RestaurantException(String.format("User with email %s already exists", userDto.getEmail()));
        }
    }

    private void setDefaultRole(UserDto userDto) {
        Role commonRole = roleService.findCommonRole();
        userDto.setRole(commonRole); // set role to default --- common
    }

    private void encodeUserPassword(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
    }

    private boolean isAuthenticatedUserAdmin() {
        UserDto authenticatedUser = findAuthenticatedUser();
        boolean isAdmin = authenticatedUser.getRole().getName().equals("ROLE_admin");
        log.info(String.format("isAuthenticatedUserAdmin: %s", isAdmin));
        return isAdmin;
    }

    private boolean isTheSameUser(Long editId) {
        UserDto authenticatedUser = findAuthenticatedUser();
        Long loggedId = authenticatedUser.getId();
        return Objects.equals(editId, loggedId);
    }

    private boolean hasPermissionToDelete(Long deleteId) {
        return !deleteId.equals(1L) && (isAuthenticatedUserAdmin() || isTheSameUser(deleteId));
    }

    private void logoutCurrentUser(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
    }

    private void deactivateUser(Long id) {
        UserDto userDto = findById(id);
        userDto.setActive(false);
        userRepository.save(userMapper.toEntity(userDto));
        log.info("Delete user with username " + userDto.getUsername() + ". (Make isActive = false)");
    }

}