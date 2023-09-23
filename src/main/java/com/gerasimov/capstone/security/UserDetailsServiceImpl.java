package com.gerasimov.capstone.security;

import com.gerasimov.capstone.entity.User;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.UserRepository;
import com.gerasimov.capstone.exception.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws RestaurantException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (!userOptional.isPresent()) {
            log.error("Username " + username + " doesn't exist in database");
            throw new RestaurantException(String.format("User %s not found", username));
        }

        User user = userOptional.get();
        log.info(String.format("Attempt to login for user=%s", user.getUsername()));

        return new UserDetailsImpl(userMapper.toDto(user));
    }
}
