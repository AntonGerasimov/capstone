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
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Optional<UserDto> findById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return Optional.of(userMapper.toDto(user.get()));
        }else{
            return Optional.empty();
        }
    }
    @Override
    public Optional<UserDto> findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            return Optional.of(userMapper.toDto(user.get()));
        } else{
            return Optional.empty();
        }
    }

    @Override
    public void login(UserDto newUser, HttpServletRequest request){
        log.info(String.format("Initiate login for username %s", newUser.getUsername()));
        Optional<UserDto> dbUserOptional = findByUsername(newUser.getUsername());
        if (dbUserOptional.isPresent()){
            UserDto dbUser = dbUserOptional.get();
            if (dbUser.isActive()){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword());
                Authentication authentication = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                HttpSession session = request.getSession(true);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                log.info(String.format("Successful login of username %s", newUser.getUsername()));
            } else{
                log.error(String.format("Can't find user with username %s", newUser.getUsername()));
                throw new RestaurantException(String.format("Can't find user with username %s", newUser.getUsername()));
            }
        }else{
            log.error(String.format("Can't find user with username", newUser.getUsername()));
            throw new RestaurantException(String.format("Can't find user with username", newUser.getUsername()));
        }
    }

    @Override
    public UserDto save(UserDto userDto) {
        if (emailExists(userDto.getEmail())){
            log.error(String.format("User with email %s already exists", userDto.getEmail()));
            throw new RestaurantException(String.format("User with email %s already exists", userDto.getEmail()));
        }
        Role commonRole = roleService.findCommonRole();
        userDto.setRole(commonRole); // set role to default --- common
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userDto.setActive(true);
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
        log.info("User with username " + user.getUsername() + " was created");
        return userDto;
    }

    @Override
    public void prepareEdit(Long editId, Authentication authentication, Model model){
        if (isAuthenticatedUserAdmin(authentication) || isTheSameUser(editId, authentication)){
            Optional<UserDto> userDto = findById(editId);
            List<Role> roles = roleService.findAll();
            model.addAttribute("user", userDto.get());
            model.addAttribute("roles", roles);
        } else {
            log.error(String.format("User doesn't have permission to edit user with id %s", editId));
            throw new RestaurantException("Sorry, you don't have access to edit this user");
        }
    }

    @Override
    @Transactional
    public void update(Long id, UserDto userDto){
        log.info("Updating user with id " + id);
        User user = userMapper.toEntity(userDto);
        Optional<User> userToUpdateOptional = userRepository.findById(id);
        if (!userToUpdateOptional.isPresent()){
            throw new RestaurantException("Can't find user to update in database");
        }
        User userToUpdate = userToUpdateOptional.get();
        if (!userToUpdate.getFirstName().equals(user.getFirstName())){
            userToUpdate.setFirstName(user.getFirstName());
        }
        if (!userToUpdate.getLastName().equals(user.getLastName())){
            userToUpdate.setLastName(user.getLastName());
        }
        if (!userToUpdate.getRole().equals(user.getRole()) && user.getRole() != null ){
            userToUpdate.setRole(user.getRole());
        }
    }


    @Override
    @Transactional
    public void delete(Long deleteId, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (isAuthenticatedUserAdmin(authentication) || isTheSameUser(deleteId, authentication)) {
            if (isTheSameUser(deleteId, authentication)){ //user wants to delete itself ---> first need to logout
                new SecurityContextLogoutHandler().logout(request, response, authentication);
            }
            User user = userRepository.findById(deleteId).orElse(null);
            if (user != null) {
                if (user.getId() != 1L){
                    log.info("Delete user with username " + user.getUsername() + ". (Make isActive = false)");
                    user.setActive(false);
                }else{
                    throw new RestaurantException("Can't delete main admin!");
                }
            } else{
                throw new RestaurantException("User that you want to delete doesn't exist");
            }
        } else{
            log.error(String.format("User doesn't have permission to edit user with id %s", deleteId));
            throw new RestaurantException("Sorry, you don't have access to edit this user");
        }
    }

    @Override
    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public String findRedirectPageAfterEdit(Long id, Authentication authentication){ //suggests that user don't edit himself on /users page
        if (isTheSameUser(id, authentication)){
            return "redirect:/users/personal-account";
        } else{
            return "redirect:/users";
        }
    }

    public String findRedirectPageAfterDelete(Long id, Authentication authentication){ //suggests that user don't delete himself on /users page
        if (isTheSameUser(id, authentication)){
            return "redirect:/";
        } else{
            return "redirect:/users";
        }
    }
    public UserDto findAuthenticatedUser(Authentication authentication){
        if ( (authentication != null) && (authentication.isAuthenticated() ) ){
            UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
            Optional<User> authenticatedUser = userRepository.findByUsername(userDetails.getUsername());
            if (authenticatedUser.isPresent()){
                return userMapper.toDto(authenticatedUser.get());
            }else{
                throw new RestaurantException(ERROR_NO_LOGGED_USER);
            }
        } else{
            throw new RestaurantException(ERROR_NO_LOGGED_USER);
        }

    }
    private boolean isAuthenticatedUserAdmin(Authentication authentication){
        UserDto authenticatedUser = findAuthenticatedUser(authentication);
        boolean isAdmin = authenticatedUser.getRole().getName().equals(ROLE_ADMIN);
        log.info(String.format("isAdmin: %s", isAdmin));
        return isAdmin;
    }

    private boolean isTheSameUser(Long editId, Authentication authentication){
        UserDto authenticatedUser = findAuthenticatedUser(authentication);
        Long loggedId = authenticatedUser.getId();
        return Objects.equals(editId, loggedId);
    }

}