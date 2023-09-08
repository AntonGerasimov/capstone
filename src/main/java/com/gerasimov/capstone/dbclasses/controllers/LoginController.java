package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.services.AuthService;
import com.gerasimov.capstone.dbclasses.util.AuthenticationResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private AuthService authService;

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public String loginUser() {
        log.info("Login attempt from username ");
        log.error("LOGIN ERROR");
        return "users/new";

//        AuthenticationResult result = authService.authenticate(username, password);
//        if (result.isSuccess()) {
//            // Authentication successful, redirect to a protected resource or home page
//                    return "users/success";
//        } else {
//            // Authentication failed, show an error message
//            redirectAttributes.addFlashAttribute("errorMessage", result.getMessage());
//            return "redirect:/login";
//        }
    }
}
