package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Role;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import com.gerasimov.capstone.dbclasses.services.RoleService;
import com.gerasimov.capstone.dbclasses.services.UserService;
import com.gerasimov.capstone.exceptions.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private RoleService roleService;
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/new")
    public String viewNewUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "users/new";
    }

    @GetMapping("/success")
    public String viewSuccessPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();
        // Pass the authenticated username to the view (HTML template)
        model.addAttribute("authenticatedUsername", authenticatedUsername);
        return "users/success";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        UserDto userDto = userService.findById(id);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", userDto);
        model.addAttribute("roles", roles);
        return "users/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto user) {
        userService.update(id, user);
        return "redirect:/users";
    }



    @PostMapping("/success")
    public String createUser(@ModelAttribute("user") UserDto userDto, Model model) {
        try{
            UserDto newUser = userService.save(userDto);
            log.info("User with username " + userDto.getUsername() + " was created");
            userService.makeLogin(newUser);

            return "users/success";
        } catch (RestaurantException restaurantException){
            // Email already exists, return an error message
            model.addAttribute("duplicateEmailException", "This email already exists.");
            return "users/new"; // Return to the registration form with the error message
        }
    }







}
