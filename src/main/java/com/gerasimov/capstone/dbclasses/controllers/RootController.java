package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.util.AuthenticationResult;
import com.gerasimov.capstone.dbclasses.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class RootController {

    private AuthService authService;
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, RedirectAttributes redirectAttributes) {
        AuthenticationResult result = authService.authenticate(username, password);

        if (result.isSuccess()) {
            // Authentication successful, redirect to a protected resource or home page
            return "redirect:/home";
        } else {
            // Authentication failed, show an error message
            redirectAttributes.addFlashAttribute("errorMessage", result.getMessage());
            return "redirect:/login";
        }
    }
}
