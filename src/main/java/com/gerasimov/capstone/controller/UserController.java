package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.service.OrderService;
import com.gerasimov.capstone.service.RoleService;
import com.gerasimov.capstone.service.UserService;
import com.gerasimov.capstone.exception.RestaurantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private OrderService orderService;

    private static final String PAGE_SIZE = "2";

    @GetMapping
    public String listUsers(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int currentPage,
            @RequestParam(name = "size", defaultValue = "2") int pageSize,
            @RequestParam(name = "isShowCommon", defaultValue = "false") Boolean isShowCommon,
            @RequestParam(name = "isShowManager", defaultValue = "false") Boolean isShowManager,
            @RequestParam(name = "isShowAdmin", defaultValue = "false") Boolean isShowAdmin
            ) {

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("username"));
        Page<UserDto> usersPage = userService.findAllByRoles(isShowCommon, isShowManager, isShowAdmin, pageable);

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("isShowCommon", isShowCommon);
        model.addAttribute("isShowManager", isShowManager);
        model.addAttribute("isShowAdmin", isShowAdmin);


        int totalPages = usersPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "users/list";
    }

    @GetMapping("/registration")
    public String viewRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "users/registration";
    }

    @GetMapping("/success-registration")
    public String viewSuccessRegistrationPage() {
        return "users/success-registration";
    }

    @GetMapping("/success-login")
    public String viewSuccessLoginPage() {
        return "users/success-login";
    }

    @GetMapping("/{id}/personal-account")
    public String viewPersonalAccount(
            @PathVariable Long id,
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE) int size,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        startDate = setDefaultStartDateIfNull(startDate);
        endDate = setDefaultEndDateIfNull(endDate);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        Page<OrderDto> ordersPage = orderService.getOrdersForAuthenticatedUserPageable(startDate, endDate, pageable);

        model.addAttribute("user", userService.findAuthenticatedUser());
        model.addAttribute("ordersPage", ordersPage);
        model.addAttribute("totalPricesMap", orderService.getTotalPrices());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("pageNumbers", findPageNumbers(ordersPage.getTotalPages()));

        return "users/personal-account";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        UserDto userDto = userService.prepareEdit(id);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", userDto);
        model.addAttribute("roles", roles);
        return "users/edit";
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        userService.delete(id, request, response, authentication);
        return userService.findRedirectPageAfterDelete(id);
    }

    @PutMapping("/{id}/edit")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto user, Authentication authentication) {
        userService.update(user);
        return userService.findRedirectPageAfterEdit(id);
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") UserDto userDto, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserDto newUser = userService.save(userDto);
            model.addAttribute("newUsername", newUser.getUsername());
            return "redirect:/login";
        } catch (RestaurantException e) {
            // Email already exists, return an error message
            redirectAttributes.addFlashAttribute("restaurantException", e.getMessage());
            return "redirect:/users/registration"; // Return to the registration form with the error message
        }
    }

    private LocalDate setDefaultStartDateIfNull(LocalDate startDate){
        return (startDate != null) ? startDate : LocalDate.of(2000, 1, 1);
    }

    private LocalDate setDefaultEndDateIfNull(LocalDate endDate){
        return (endDate != null) ? endDate : LocalDate.now();
    }

    private List<Integer> findPageNumbers(int totalPages){
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            return pageNumbers;
        }
        return new ArrayList<>();
    }

}
