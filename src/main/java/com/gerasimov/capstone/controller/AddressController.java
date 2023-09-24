package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.AddressDtoLight;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/addresses")
    public ModelAndView getAllAddresses(Model model) {
        List<AddressDto> addresses = addressService.findAll();
        model.addAttribute("addresses", addresses);
        return new ModelAndView("addresses");
    }

    @GetMapping("/users/{id}/addresses")
    public String viewUserAddresses(
            @PathVariable Long id,
            Model model,
            Authentication authentication
    ) {
        List<AddressDto> addresses = addressService.findAvailableForAuthenticatedUser(authentication);
        model.addAttribute("addresses", addresses);


        return "users/addresses";
    }

    @GetMapping("/users/{userId}/addresses/add")
    public String viewNewAddressForm(
            @PathVariable Long userId,
            Model model,
            HttpServletRequest request
    ) {
        model.addAttribute("address", new AddressDto());


        String referrer = request.getHeader("referer");
        model.addAttribute("referrer", referrer);
        return "users/new-address";
    }

    @GetMapping("/users/{userId}/addresses/{addressId}/edit")
    public String viewAddressEditForm(@PathVariable Long userId, @PathVariable Long addressId, Model model) {
        AddressDtoLight addressDtoLight = addressService.findLightById(addressId);
        model.addAttribute("address", addressDtoLight);
        return "addresses/edit-address";
    }

    @PostMapping("/users/{userId}/addresses/add")
    @ResponseBody
    public String addNewAddress(
            @PathVariable Long userId,
            @ModelAttribute("address") AddressDto addressDto,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        try {
            AddressDto newAddress = addressService.save(addressDto);
            log.info(String.format("New address was created: %s", newAddress.toString()));
            redirectAttributes.addAttribute("userId", userId);
            return "New address added successfully";
        } catch (RestaurantException restaurantException) {
            model.addAttribute("Restaurant exception", "Error!");
            redirectAttributes.addAttribute("userId", userId);
            return "An error occured during adding new address";
        }
    }

    @PutMapping("/users/{userId}/addresses/{addressId}/edit")
    public String updateAddress(@PathVariable Long userId, @PathVariable Long addressId, @ModelAttribute AddressDtoLight addressDtoLight, Authentication authentication, RedirectAttributes redirectAttributes) {
        addressService.update(addressDtoLight);
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
