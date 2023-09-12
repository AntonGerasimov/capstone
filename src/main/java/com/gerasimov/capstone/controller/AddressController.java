package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/addresses")
    public ModelAndView getAllAddresses(Model model){
        List<AddressDto> addresses = addressService.findAll();
        model.addAttribute("addresses", addresses);
        return new ModelAndView("addresses");
    }
}
