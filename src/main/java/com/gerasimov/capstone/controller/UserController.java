package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.service.OrderService;
import com.gerasimov.capstone.service.RoleService;
import com.gerasimov.capstone.service.UserService;
import com.gerasimov.capstone.exception.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private OrderService orderService;

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
    public String viewSuccessRegistrationPage() {
        return "users/success-registration";
    }

    @GetMapping("/success-login")
    public String viewSuccessLoginPage() {
        return "users/success-login";
    }

    @GetMapping("/{id}/personal-account")
    public String viewPersonalAccount(@PathVariable Long id, Model model, Pageable pageable) {
        try {
            UserDto authenticatedUser = userService.findAuthenticatedUser();
            model.addAttribute("user", authenticatedUser);
            model.addAttribute("groupedOrders", orderService.getGroupedOrdersForAuthenticatedUser());
            model.addAttribute("totalPricesMap", orderService.getTotalPrices());
            return "users/personal-account";
        } catch (RestaurantException e) {
            return "users/registration";
        }
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        UserDto userDto = userService.prepareEdit(id);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", userDto);
        model.addAttribute("roles", roles);
        return "users/edit";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        userService.delete(id, request, response, authentication);
        return userService.findRedirectPageAfterDelete(id);
    }

    @PutMapping("/{id}/edit")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto user, Authentication authentication) {
        userService.update(user);
        return userService.findRedirectPageAfterEdit(id);
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") UserDto userDto, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserDto newUser = userService.save(userDto);
            model.addAttribute("newUsername", newUser.getUsername());
            return "redirect:/login";
        } catch (RestaurantException e) {
            // Email already exists, return an error message
            redirectAttributes.addFlashAttribute("restaurantException", e.getMessage());
            return "redirect:/users/registration"; // Return to the registration form with the error message
        }
    }


}
