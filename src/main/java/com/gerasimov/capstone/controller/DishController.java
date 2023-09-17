package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class DishController {
    private final DishService dishService;


    @GetMapping("/menu")
    public String getAllDishes(Model model){
        List<DishDto> menuItems = dishService.findAvailable();

        // Group the menu items by category
        Map<String, List<DishDto>> menuItemsByCategory = menuItems.stream()
                .collect(Collectors.groupingBy(DishDto::getCategory));

        model.addAttribute("menuItemsByCategory", menuItemsByCategory);
        return "menu";
    }
}
