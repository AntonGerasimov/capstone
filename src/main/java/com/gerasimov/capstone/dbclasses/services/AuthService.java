package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.util.AuthenticationResult;
import com.gerasimov.capstone.dbclasses.entity.User;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public AuthenticationResult authenticate(String username, String providedPassword) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            String storedHashedPassword = user.getPassword();

            if (passwordEncoder.matches(providedPassword, storedHashedPassword)) {
                // Authentication successful
                return new AuthenticationResult(true, "Login successful");
            } else {
                // Authentication failed
                return new AuthenticationResult(false, "Invalid username or password");
            }
        } else {
            // User not found
            return new AuthenticationResult(false, "User not found");
        }
    }
}
