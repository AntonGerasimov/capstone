package com.gerasimov.capstone.dbclasses.services.impl;


import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.entity.Order;
import com.gerasimov.capstone.dbclasses.entity.Role;
import com.gerasimov.capstone.dbclasses.entity.User;
import com.gerasimov.capstone.dbclasses.mappers.UserMapper;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import com.gerasimov.capstone.dbclasses.repositories.OrderRepository;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import com.gerasimov.capstone.dbclasses.services.AddressService;
import com.gerasimov.capstone.dbclasses.services.OrderService;
import com.gerasimov.capstone.dbclasses.services.UserService;
import com.gerasimov.capstone.exceptions.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AddressRepository addressRepository;
    private OrderRepository orderRepository;
    private AddressService addressService;
    private OrderService orderService;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto findById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return userMapper.toDto(user.get());
        }else{
            return null;
        }
    }

    @Override
    public UserDto save(UserDto userDto) {
        if (emailExists(userDto.getEmail())){
            throw new RestaurantException("Duplicate user with email: " + userDto.getEmail());
        }
        Role role = roleRepository.findById(1L).orElse(null);
        userDto.setRole(role);
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
        return userDto;
    }

    @Override
    public void makeLogin(UserDto newUser){
        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getUsername());

        // Create an authentication token for the new user with the "common" role
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Set the authentication token in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Perform the login (authenticate) using the AuthenticationManager
        Authentication authenticatedUser = authenticationManager.authenticate(authentication);

        // If needed, you can perform additional actions here after successful login
        // For example, you can store additional user-related data in the session.

        if (authenticatedUser.isAuthenticated()) {
            // Authentication was successful
            log.info("Successful login of new user with username " + newUser.getUsername());
        }


//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword());
//        Authentication authentication = authenticationManager.authenticate(authRequest);
//            SecurityContextHolder.getContext().setAuthentication(authentication);

        // Add remember-me cookie if needed
//            rememberMeServices.onLoginSuccess(request, response, authentication);
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto){
        log.info("Updating user with id " + id);
        User userToUpdate = userRepository.findById(id).orElse(null);
        log.info("Before Update. First name: " + userToUpdate.getFirstName() );
        log.info("Before Update. Last name: " + userToUpdate.getLastName() );
        log.info("After Update. First name: " + userDto.getFirstName() );
        log.info("After Update. Last name: " + userDto.getLastName() );
        if (userToUpdate != null){
            userToUpdate.setFirstName(userDto.getFirstName());
            userToUpdate.setLastName(userDto.getLastName());
            userToUpdate.setRole(userDto.getRole());
            return userDto;
        } else{
            return null;
        }

    }

    @Override
    public void delete(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            log.info("Delete user with username " + user.getUsername());
            List<Address> addresses = addressRepository.findByUser(user);
            addresses.forEach(address -> addressService.delete(address.getId()));
            user.setRole(null);
            userRepository.deleteById(userId);
        }
    }

    @Override
    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

}