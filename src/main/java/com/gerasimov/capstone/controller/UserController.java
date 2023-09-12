package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.service.RoleService;
import com.gerasimov.capstone.service.UserService;
import com.gerasimov.capstone.exception.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/registration")
    public String viewRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "users/registration";
    }

    @GetMapping("/success-registration")
    public String viewSuccessRegistrationPage(){
        return "users/success-registration";
    }

    @GetMapping("/success-login")
    public String viewSuccessLoginPage(){
        return "users/success-login";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<UserDto> userDto = userService.findById(id);
        if (userDto.isPresent()){
            List<Role> roles = roleService.findAll();
            model.addAttribute("user", userDto);
            model.addAttribute("roles", roles);
            return "users/edit";
        }else{
            throw new RestaurantException("User that you want to edit can't be found");
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Authentication authentication) {
        userService.delete(id);
        return "redirect:/users";
    }


    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto user) {
        userService.update(id, user);
        return "redirect:/users";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") UserDto userDto, Model model) {
        try{
            Optional<UserDto> newUser = userService.save(userDto);
            if (newUser.isPresent()){
                model.addAttribute("newUsername", newUser.get().getUsername());
                log.info("User with username " + newUser.get().getUsername() + " was created");
                return "redirect:/users/success-registration";
            } else{
                throw new RestaurantException("An error occured during save process");
            }
        } catch (RestaurantException restaurantException){
            // Email already exists, return an error message
            model.addAttribute("Restaurant exception", "This email already exists.");
            return "redirect:/users/registration"; // Return to the registration form with the error message
        }
    }





}
