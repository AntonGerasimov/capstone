package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/addresses")
    public ModelAndView getAllAddresses(Model model){
        List<AddressDto> addresses = addressService.findAll();
        model.addAttribute("addresses", addresses);
        return new ModelAndView("addresses");
    }

    @GetMapping("/users/personal-account/addresses")
    public String viewUserAddresses(Model model, Authentication authentication){
        List<AddressDto> addresses = addressService.findAllForAuthenticatedUser(authentication);
        model.addAttribute("addresses", addresses);
        return "users/addresses";
    }

    @GetMapping("/users/personal-account/addresses/add")
    public String viewNewAddressForm(Model model){
        model.addAttribute("address", new AddressDto());
        return "users/new-address";
    }

    @PostMapping("/users/personal-account/addresses/new")
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
