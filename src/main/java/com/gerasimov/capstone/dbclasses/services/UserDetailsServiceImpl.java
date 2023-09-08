package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import com.gerasimov.capstone.exceptions.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws RestaurantException {
        com.gerasimov.capstone.dbclasses.entity.User user = userRepository.findByUsername(username);
        log.info("Attempt to login by username " + username);
        if (user == null) {
            log.error("Username " + username + " doesn't exist in database");
            throw new RestaurantException("User not found. Username: " + username);
        }
        log.info("Success login. Username: " + user.getUsername() + ". Role: " + user.getRole().getName());

        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();

        return userDetails;
    }

}
