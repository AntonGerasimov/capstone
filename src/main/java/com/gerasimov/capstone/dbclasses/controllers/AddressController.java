//package com.gerasimov.capstone.dbclasses.controllers;
//
//import com.gerasimov.capstone.dbclasses.domain.AddressDto;
//import com.gerasimov.capstone.dbclasses.services.AddressService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//
//@RestController
//public class AddressController {
//    private final AddressService addressService;
//    @Autowired
//    public AddressController(AddressService addressService) {
//        this.addressService = addressService;
//    }
//
//    @GetMapping("/addresses")
//    public ModelAndView getAllAddresses(Model model){
//        List<AddressDto> addresses = addressService.getAllAddresses();
//        model.addAttribute("addresses", addresses);
//        return new ModelAndView("addresses");
//    }
//}
