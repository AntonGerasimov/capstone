package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.DishDto;
import com.gerasimov.capstone.dbclasses.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class DishController {
    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public ModelAndView getAllDishes(Model model){
        List<DishDto> dishes = dishService.getAllDishes();
        model.addAttribute("dishes", dishes);
        return new ModelAndView("dishes");
    }
}
