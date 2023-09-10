package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import com.gerasimov.capstone.exceptions.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws RestaurantException {
        com.gerasimov.capstone.dbclasses.entity.User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("Username " + username + " doesn't exist in database");
            throw new RestaurantException("User not found. Username: " + username);
        }

        log.info("Attempt to login. Username: " + user.getUsername() + ". Role: " + user.getRole().getName());

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();
    }


}
