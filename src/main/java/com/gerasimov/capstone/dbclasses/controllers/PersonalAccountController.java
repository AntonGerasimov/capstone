package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Role;
import com.gerasimov.capstone.dbclasses.services.AddressService;
import com.gerasimov.capstone.dbclasses.services.RoleService;
import com.gerasimov.capstone.dbclasses.services.UserService;
import com.gerasimov.capstone.exceptions.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/users/personal-account")
public class PersonalAccountController {
    private RoleService roleService;
    private UserService userService;
    private AddressService addressService;

    @GetMapping
    public String viewPersonalAccount(){
        return "users/personal-account";
    }

    @GetMapping("/edit/{username}")
    public String editUser(@PathVariable String username, Model model) {
        UserDto userDto = userService.findByUsername(username);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", userDto);
        model.addAttribute("roles", roles);
        return "users/edit-personal-account";
    }

    @GetMapping("/delete/{username}")
    public String deleteUserPersonalAccount(@PathVariable String username, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        Long id = userService.findByUsername(username).getId();
        userService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/addresses")
    public String viewUserAddresses(Model model, Authentication authentication){
        UserDto authenticatedUser = userService.findByUsername(authentication.getName());
        List<AddressDto> addresses = addressService.findAllByUser(authenticatedUser);
        model.addAttribute("addresses", addresses);
        return "users/addresses";
    }

    @GetMapping("/addresses/add")
    public String viewNewAddressForm(Model model){
        model.addAttribute("address", new AddressDto());
        return "users/new-address";
    }

    @GetMapping("/orders")
    public String viewUserOrders(){
        return "users/orders";
    }

    @PostMapping("/edit/{username}")
    public String updateUser(@PathVariable String username, @ModelAttribute UserDto user) {
        userService.updateByUsername(username, user);
        return "redirect:/users/personal-account";
    }

    @PostMapping("/addresses/new")
    public String addNewAddress(@ModelAttribute("address") AddressDto addressDto, Model model){
        try{
            AddressDto newAddress = addressService.save(addressDto);
            log.info("New address was created. Street: " + newAddress.getStreet() + ". House: " + newAddress.getHouse() + ". Apartment: " + newAddress.getApartment() );
            return "redirect:/users/personal-account/addresses";
        } catch (RestaurantException restaurantException){
            model.addAttribute("Restaurant exception", "Error!");
            return "redirect:/users/personal-account/addresses/new"; // Return to the registration form with the error message
        }
    }
}
