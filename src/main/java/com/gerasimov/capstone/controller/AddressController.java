package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @GetMapping("/users/{id}/addresses")
    public String viewUserAddresses(@PathVariable Long id, Model model, Authentication authentication){
        List<AddressDto> addresses = addressService.findAllForAuthenticatedUser(authentication);
        model.addAttribute("addresses", addresses);
        return "users/addresses";
    }

    @GetMapping("/users/{id}/addresses/add")
    public String viewNewAddressForm(@PathVariable Long id, Model model){
        model.addAttribute("address", new AddressDto());
        return "users/new-address";
    }

    @GetMapping("/users/{userId}/addresses/{addressId}/edit")
    public String viewAddressEditForm(@PathVariable Long userId, @PathVariable Long addressId, Model model){
        AddressDto addressDto = addressService.findById(addressId);
        model.addAttribute("address", addressDto);
        return "addresses/edit-address";
    }

    @PostMapping("/users/{id}/addresses/add")
    public String addNewAddress(@PathVariable Long id,@ModelAttribute("address") AddressDto addressDto, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        try{
            AddressDto newAddress = addressService.save(addressDto, authentication);
            log.info("New address was created. Street: " + newAddress.getStreet() + ". House: " + newAddress.getHouse() + ". Apartment: " + newAddress.getApartment() );
            redirectAttributes.addAttribute("id", id);
            return "redirect:/users/{id}/addresses";
        } catch (RestaurantException restaurantException){
            model.addAttribute("Restaurant exception", "Error!");
            redirectAttributes.addAttribute("id", id);
            return "redirect:/users/{id}/addresses/new"; // Return to the registration form with the error message
        }
    }

    @PostMapping("/users/{userId}/addresses/{addressId}/edit")
    public String updateAddress(@PathVariable Long userId, @PathVariable Long addressId, @ModelAttribute AddressDto addressDto, Authentication authentication, RedirectAttributes redirectAttributes) {
        addressService.update(addressId, addressDto, userId);
        redirectAttributes.addAttribute("userId", userId);
        return "redirect:/users/{userId}/addresses";
    }


    @DeleteMapping("/users/{userId}/addresses/{addressId}")
    public String deleteAddress(@PathVariable Long userId, @PathVariable Long addressId, RedirectAttributes redirectAttributes) {
        addressService.delete(addressId);
        redirectAttributes.addAttribute("id", userId);
        return "redirect:/users/{id}/addresses";
    }

}
