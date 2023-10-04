package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.service.OrderService;
import com.gerasimov.capstone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class RootController {

    private UserService userService;
    private OrderService orderService;

    @GetMapping("/login")
    public String loginNotDefault(Model model) {
        model.addAttribute("newUser", new UserDto());
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        log.info("Logout");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String viewAfterLogin(
            @ModelAttribute UserDto newUser,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        log.info("Post request for logging");
        try {
            userService.login(newUser, request);
            return "redirect:/";
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Invalid password");
            return "redirect:/login";
        } catch (RestaurantException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/orders")
    public String viewAllOrders(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate

    ){
        startDate = setDefaultStartDateIfNull(startDate);
        endDate = setDefaultEndDateIfNull(endDate);


        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));

        Page<OrderDto> ordersPage = orderService.findAll(startDate, endDate, pageable);

        model.addAttribute("ordersPage", ordersPage);
        model.addAttribute("pageNumbers", findPageNumbers(ordersPage.getTotalPages()));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "orders/all-orders";
    }

    @GetMapping("/orders/{orderId}/edit")
    public String editOrder(@PathVariable Long orderId, Model model, HttpServletRequest request){

        OrderDto orderDto = orderService.findById(orderId);
        model.addAttribute("order", orderDto);
        model.addAttribute("statuses", orderService.getStatusesList());
        return "orders/edit-order";
    }

    @PutMapping("/orders/{orderId}/edit")
    public String changeOrderStatus(@PathVariable Long orderId, @RequestParam String status, Model model){
        orderService.changeOrderStatus(orderId, status);
        log.info("Order status was changed");
        return "redirect:/orders";
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
