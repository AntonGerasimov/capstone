package com.gerasimov.capstone.dbclasses.controllers;

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
    public String showAddUserForm(@ModelAttribute("user") User user) {
        return "new-user"; // Return the name of the Thymeleaf template for the new user page
    }


    @PostMapping
    public String addUser(@ModelAttribute("user") User user) {
        String firstName = user.getFullName().getFirstName();
        String lastName = user.getFullName().getLastName();
        FullName fullName = new FullName(firstName, lastName);


        User updatedUser =user.withFullName(firstName, lastName);
        userService.saveUser(updatedUser);
        return "redirect:/users"; // Redirect to the user list page after successful addition
    }
}
