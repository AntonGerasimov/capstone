package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class DishController {
    private final DishService dishService;


    @GetMapping("/menu")
    public String getAllDishes(Model model){
        List<DishDto> dishes = dishService.findAvailable();
        model.addAttribute("menuItems", dishes);
        return "menu";
    }
}
