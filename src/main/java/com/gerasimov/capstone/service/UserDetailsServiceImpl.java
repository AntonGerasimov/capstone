package com.gerasimov.capstone.service;

import com.gerasimov.capstone.repository.RoleRepository;
import com.gerasimov.capstone.repository.UserRepository;
import com.gerasimov.capstone.exception.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws RestaurantException {
        Optional<com.gerasimov.capstone.entity.User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            log.error("Username " + username + " doesn't exist in database");
            throw new RestaurantException("User not found. Username: " + username);
        }
        com.gerasimov.capstone.entity.User user= userOptional.get();
        log.info("Attempt to login. Username: " + user.getUsername() + ". Role: " + user.getRole().getName());

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();
    }


}
