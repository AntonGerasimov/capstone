package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @GetMapping
    public ModelAndView getAllUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return new ModelAndView("users");
    }
    @GetMapping("/new")
    public String showAddUserForm(Model model) {
        return "new-user"; // Return the name of the Thymeleaf template for the new user page
    }

    @PostMapping
    public String addUser(
            @RequestParam String firstName,
            @RequestParam String lastName ,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role
    ) {
        boolean isActive = true;
        UserDto user = new UserDto(null, firstName, lastName, email, username, password, role, isActive);
        userService.saveUser(user);
        return "redirect:/users"; // Redirect to the user list page after successful addition
    }
}
