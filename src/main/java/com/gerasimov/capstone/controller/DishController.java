package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.cart.Cart;
import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.service.DishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@Slf4j
public class DishController {
    private final DishService dishService;






    @GetMapping("/dishes/{id}")
    public String viewDish(@PathVariable Long id, Model model){
        DishDto dishDto = dishService.findById(id);
        model.addAttribute("dish", dishDto);
        return "dish";
    }


}
