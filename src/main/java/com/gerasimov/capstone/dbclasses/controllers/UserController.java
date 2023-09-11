package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Role;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import com.gerasimov.capstone.dbclasses.services.AddressService;
import com.gerasimov.capstone.dbclasses.services.RoleService;
import com.gerasimov.capstone.dbclasses.services.UserService;
import com.gerasimov.capstone.exceptions.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        return "users/successRegistration";
    }

    @GetMapping("/success-login")
    public String viewSuccessLoginPage(){
        return "users/successLogin";
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
            UserDto newUser = userService.save(userDto);
            model.addAttribute("newUsername", newUser.getUsername());
            log.info("User with username " + newUser.getUsername() + " was created");
            return "redirect:/users/success-registration";
        } catch (RestaurantException restaurantException){
            // Email already exists, return an error message
            model.addAttribute("Restaurant exception", "This email already exists.");
            return "redirect:/users/registration"; // Return to the registration form with the error message
        }
    }





}
