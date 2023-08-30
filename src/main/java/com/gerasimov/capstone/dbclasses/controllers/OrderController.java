package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.domain.OrderDto;
import com.gerasimov.capstone.dbclasses.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ModelAndView getAllOrders(Model model){
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return new ModelAndView("orders");
    }
}
