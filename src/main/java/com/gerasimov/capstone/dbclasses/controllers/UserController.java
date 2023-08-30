package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.FullAddress;
import com.gerasimov.capstone.dbclasses.domain.FullName;
import com.gerasimov.capstone.dbclasses.domain.User;
import com.gerasimov.capstone.dbclasses.entity.UserEntity;
import com.gerasimov.capstone.dbclasses.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getAllUsers(Model model){
        List<User> users = userService.getAllUsers();
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
        FullName fullName = new FullName(firstName, lastName);
        boolean isActive = true;
        User user = new User(null, fullName, email, username, password, role, isActive);
        userService.saveUser(user);
        return "redirect:/users"; // Redirect to the user list page after successful addition
    }
}
