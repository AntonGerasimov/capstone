package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list"; // This maps to the "list.html" Thymeleaf template
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "users/new"; // This maps to the "new.html" Thymeleaf template
    }


    @PostMapping
    public String createUser(@ModelAttribute("user") UserDto userDto, Model model) {
        if (userService.emailExists(userDto.getEmail())) {
            // Email already exists, return an error message
            model.addAttribute("emailExistsError", "This email already exists.");
            return "users/new"; // Return to the registration form with the error message
        }
        userService.save(userDto);
        model.addAttribute("username", userDto.getUsername());
        return "users/success";
    }

}
