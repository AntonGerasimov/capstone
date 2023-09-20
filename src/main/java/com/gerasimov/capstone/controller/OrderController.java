package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/orders")
    public ModelAndView getAllOrders(Model model){
        List<OrderDto> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return new ModelAndView("orders");
    }

    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model){
        OrderDto orderDto = orderService.findById(id);
        model.addAttribute("order", orderDto);
        List<OrderItemDto> orderItemDtos = orderService.findOrderItems(orderDto);
        model.addAttribute("items", orderItemDtos);
        return "orders/order";
    }

    @GetMapping("/users/{id}/orders")
    public String viewUserOrders(@PathVariable Long id){
        return "users/orders";
    }


}
