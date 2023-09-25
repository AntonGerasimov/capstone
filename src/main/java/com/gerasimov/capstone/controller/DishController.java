package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.AddressDtoLight;
import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.service.DishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
@AllArgsConstructor
@Slf4j
public class DishController {
    private final DishService dishService;

    @GetMapping("/dishes/{id}")
    public String viewDish(@PathVariable Long id, Model model) {
        DishDto dishDto = dishService.findById(id);
        model.addAttribute("dish", dishDto);
        return "dish";
    }

    @GetMapping("/dishes/{id}/edit")
    public String editDish(@PathVariable Long id, Model model){
        DishDto dishDto = dishService.findById(id);
        model.addAttribute("dish", dishDto);
        return "dishes/edit-dish";
    }

    @GetMapping("/dishes/add")
    public String viewNewDishForm(Model model) {
        log.info("Get mapping /dishes/add");
        model.addAttribute("dish", new DishDto());
        return "dishes/add";
    }

    @PostMapping("/dishes/add")
    public String saveDish(
            @ModelAttribute("dish") DishDto dishDto,
            @RequestParam("image") MultipartFile imageFile,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            DishDto newDish = dishService.save(dishDto);
            log.info(String.format("New dish was created: ", newDish.toString()));
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    // Define the directory where you want to save the image (e.g., /templates/static/images)
                    String uploadDir = "src/main/resources/static/images/";

                    // Generate a unique filename for the uploaded image
                    String fileName = String.format("%d.jpeg", newDish.getId());
                    Path filePath = Path.of(uploadDir, fileName);

                    // Copy the image file to the destination directory
                    Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "redirect:/menu";
        } catch (RestaurantException e) {
            redirectAttributes.addFlashAttribute("restaurantException", e.getMessage());
            return "redirect:/dishes/add";
        }
    }

    @PutMapping("/dishes/{dishId}/edit")
    public String updateDish(@PathVariable Long dishId, @ModelAttribute DishDto dishDto) {
        dishService.update(dishDto);
        return "redirect:/menu";
    }
    @DeleteMapping("/dishes/{dishId}")
    public String deleteDish(@PathVariable Long dishId){
        dishService.delete(dishId);
        return "redirect:/menu";
    }


}
