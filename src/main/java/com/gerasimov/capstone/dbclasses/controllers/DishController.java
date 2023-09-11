package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.DishDto;
import com.gerasimov.capstone.dbclasses.services.DishService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class DishController {
    private final DishService dishService;


    @GetMapping("/menu")
    public String getAllDishes(Model model){
        List<DishDto> dishes = dishService.findAllAvailable();
        model.addAttribute("dishes", dishes);
        return "menu";
    }
}
