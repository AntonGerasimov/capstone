package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.service.DishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class DishController {
    private final DishService dishService;

    @GetMapping("/dishes")
    public String viewManageDishes(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int currentPage,
            @RequestParam(name = "size", defaultValue = "3") int pageSize,
            @RequestParam(value = "minPrice", defaultValue = "0.0") double minPrice,
            @RequestParam(value = "maxPrice", defaultValue = "1000.0") double maxPrice
            ){

        Page<DishDto> menuPage = dishService.findManageMenuPage(
                PageRequest.of(currentPage - 1, pageSize),
                null, minPrice, maxPrice
        );

        model.addAttribute("menuPage", menuPage);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        int totalPages = menuPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "dishes/list";
    }

    @GetMapping("/dishes/{id}")
    public String viewDish(@PathVariable Long id, Model model) {
        DishDto dishDto = dishService.findById(id);
        model.addAttribute("dish", dishDto);
        return "dishes/dish";
    }

    @GetMapping("/dishes/add")
    public String viewNewDishForm(Model model, HttpServletRequest request) {
        log.info("Get mapping /dishes/add");
        model.addAttribute("dish", new DishDto());

        String referrer = request.getHeader("referer");
        model.addAttribute("referrer", referrer);
        return "dishes/add";
    }
    @GetMapping("/dishes/{id}/edit")
    public String editDish(@PathVariable Long id, Model model, HttpServletRequest request){
        DishDto dishDto = dishService.findById(id);
        model.addAttribute("dish", dishDto);

        String referrer = request.getHeader("referer");
        model.addAttribute("referrer", referrer);
        return "dishes/edit-dish";
    }



    @PostMapping("/dishes/add")
    @ResponseBody
    public void addNewDish(
            @ModelAttribute("dish") DishDto dishDto,
            @RequestParam("image") MultipartFile imageFile,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        DishDto newDish = dishService.save(dishDto, imageFile);
        log.info(String.format("New dish was created: %s", newDish.toString()));
    }

    @PutMapping("/dishes/{dishId}/edit")
    @ResponseBody
    public void updateDish(
            @PathVariable Long dishId,
            @ModelAttribute DishDto dishDto,
            @RequestParam("image") MultipartFile imageFile
    ) {
        dishService.update(dishDto, imageFile);
    }

    @PutMapping("/dishes/{dishId}/make-available")
    @ResponseBody
    public void makeDishAvailable(@PathVariable Long dishId){
        dishService.makeAvailable(dishId);
    }

    @DeleteMapping("/dishes/{dishId}")
    @ResponseBody
    public void deleteDish(@PathVariable Long dishId){
        dishService.delete(dishId);
    }


}
