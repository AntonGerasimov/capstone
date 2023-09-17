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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String ROLE_ADMIN = "ROLE_admin";
    private static final String ERROR_NO_LOGGED_USER = "Can't find authenticated user. Please, sign in";
    private UserRepository userRepository;
    private RoleService roleService;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new RestaurantException(String.format("Can't find user with id %d in database", id)));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new RestaurantException(String.format("Can't find user with username %s in database", username)));
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
        if (emailExists(userDto.getEmail())) {
            log.error(String.format("User with email %s already exists", userDto.getEmail()));
            throw new RestaurantException(String.format("User with email %s already exists", userDto.getEmail()));
        }
        Role commonRole = roleService.findCommonRole();
        userDto.setRole(commonRole); // set role to default --- common
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
        log.info("User with username " + user.getUsername() + " was created");
        return userDto;
    }

    @Override
    public UserDto prepareEdit(Long editId, Authentication authentication) {
        if (isAuthenticatedUserAdmin(authentication) || isTheSameUser(editId, authentication)) {
            return findById(editId);
        } else {
            log.error(String.format("User doesn't have permission to edit user with id %s", editId));
            throw new RestaurantException("Sorry, you don't have access to edit this user");
        }
    }

    @Override
    @Transactional
    public void update(Long id, UserDto updatedUser) {
        log.info("Updating user with id " + id);
        UserDto userToUpdate = findById(id);
        updatedUser.setPassword(userToUpdate.getPassword());
        if (updatedUser.getRole() == null) {
            updatedUser.setRole(userToUpdate.getRole());
        }
        userRepository.save(userMapper.toEntity(updatedUser));
    }


    @Override
    @Transactional
    public void delete(Long deleteId, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (hasPermissionToDelete(deleteId, authentication)) {

            if (deleteId.equals(1L)) {
                throw new RestaurantException("Can't delete main admin!");
            }

            if (isTheSameUser(deleteId, authentication)) { // User wants to delete itself ---> first need to logout
                logoutCurrentUser(request, response, authentication);
            }

            deactivateUser(deleteId);
        } else {
            log.error(String.format("User doesn't have permission to edit user with id %s", deleteId));
            throw new RestaurantException("Sorry, you don't have access to edit this user");
        }
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public String findRedirectPageAfterEdit(Long id, Authentication authentication) { //suggests that user don't edit himself on /users page
        if (isTheSameUser(id, authentication)) {
            return String.format("redirect:/users/%d/personal-account", id);
        } else {
            return "redirect:/users";
        }
    }

    public String findRedirectPageAfterDelete(Long id, Authentication authentication) { //suggests that user don't delete himself on /users page
        if (isTheSameUser(id, authentication)) {
            return "redirect:/";
        } else {
            return "redirect:/users";
        }
    }

    public UserDto findAuthenticatedUser(Authentication authentication) {
        if ((authentication != null) && (authentication.isAuthenticated())) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return findByUsername(userDetails.getUsername());
        } else {
            throw new RestaurantException(ERROR_NO_LOGGED_USER);
        }
    }

    private boolean isAuthenticatedUserAdmin(Authentication authentication) {
        UserDto authenticatedUser = findAuthenticatedUser(authentication);
        boolean isAdmin = authenticatedUser.getRole().getName().equals(ROLE_ADMIN);
        log.info(String.format("isAuthenticatedUserAdmin: %s", isAdmin));
        return isAdmin;
    }

    private boolean isTheSameUser(Long editId, Authentication authentication) {
        UserDto authenticatedUser = findAuthenticatedUser(authentication);
        Long loggedId = authenticatedUser.getId();
        return Objects.equals(editId, loggedId);
    }

    private boolean hasPermissionToDelete(Long deleteId, Authentication authentication) {
        return isAuthenticatedUserAdmin(authentication) || isTheSameUser(deleteId, authentication);
    }

    private void logoutCurrentUser(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
    }

    private void deactivateUser(Long deleteId) {
        UserDto userDto = findById(deleteId);
        userDto.setActive(false);
        userRepository.save(userMapper.toEntity(userDto));
        log.info("Delete user with username " + userDto.getUsername() + ". (Make isActive = false)");
    }

}