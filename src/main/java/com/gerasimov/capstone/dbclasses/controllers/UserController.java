package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Role;
import com.gerasimov.capstone.dbclasses.entity.User;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import com.gerasimov.capstone.dbclasses.services.UserService;
import com.gerasimov.capstone.exceptions.DuplicateUserException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "users/new";
    }

    @GetMapping("/success")
    public String userSuccessReg(Model model){
        return "users/success";
    }

    @PostMapping
    public String updateRole(@RequestBody Map<String, String> requestBody, Model model) {
        Long userId = Long.parseLong(requestBody.get("userId"));
        Role newRole = roleRepository.findByName(requestBody.get("newRole"));

        // Fetch the user from the database and update the role
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(newRole);
//            return ResponseEntity.ok("Role updated successfully");
        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "redirect:/users";
    }



    @PostMapping("/success")
    public String createUser(@ModelAttribute("user") UserDto userDto, Model model) {
        try{
            userService.save(userDto);
            model.addAttribute("username", userDto.getUsername());
            return "users/success";
        } catch (DuplicateUserException duplicateUserException){
            // Email already exists, return an error message
            model.addAttribute("duplicateEmailException", "This email already exists.");
            return "users/new"; // Return to the registration form with the error message
        }
    }







}
