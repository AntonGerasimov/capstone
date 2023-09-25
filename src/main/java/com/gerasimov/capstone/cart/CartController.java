package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@AllArgsConstructor
public class CartController {
    private CartService cartService;
    private OrderService orderService;

    @GetMapping("/")
    public String showMainPage(Model model) {

        model.addAttribute("hotSales", cartService.getHotSales());
        model.addAttribute("menuItemsByCategory", cartService.getMenu());
        model.addAttribute("dishQuantityMap", cartService.createDishQuantityMap());

        return "index";
    }

    @GetMapping("/menu")
    public String getMenu(Model model) {

        model.addAttribute("menuItemsByCategory", cartService.getMenu());
        model.addAttribute("dishQuantityMap", cartService.createDishQuantityMap());
        return "menu";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {

        model.addAttribute("cartItems", cartService.getCart());
        model.addAttribute("totalPrice", cartService.calculateTotalPrice());

        return "cart/cart"; // Return the Thymeleaf template name
    }

    @GetMapping("/checkout")
    public String viewCheckout(Model model) {
        model.addAttribute("cartItems", cartService.getCart());
        model.addAttribute("addresses", cartService.findAddressesForUser());
        model.addAttribute("totalPrice", cartService.calculateTotalPrice());
        return "cart/checkout";
    }

    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        OrderDto orderDto = orderService.findById(id);
        model.addAttribute("order", orderDto);
        model.addAttribute("items", orderService.findOrderItems(orderDto));
        model.addAttribute("totalPrice", orderService.calcTotalPrice(orderDto));
        return "orders/order";
    }

    @PostMapping("/cart/{dishId}/add")
    @ResponseBody
    public String addItemToCart(@PathVariable Long dishId, HttpServletResponse response, Authentication authentication) {
        if ((authentication == null) || (!authentication.isAuthenticated())) {
            // User is not authenticated, so set a 403 status code and return an error message.
            log.info("Not authenticated user");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Set the status code to 403
            return "You do not have permission to perform this action.";
        }
        log.info("Authenticated user");
        log.info("Post request for adding item to cart");
        cartService.addToCart(dishId);
        return "Dish added to cart successfully";
    }

    @PostMapping("/cart/{id}/remove")
    @ResponseBody
    public String removeItemFromCart(@PathVariable Long id) {
        log.info("Post request for adding item to cart");
        cartService.removeFromCart(id);
        return "Dish added to cart successfully";
    }

    @PostMapping("/cart/make-order")
    @ResponseBody
    public String makeOrder(@RequestParam("selectedAddressId") Long selectedAddressId) {
        log.info("Begin making order");
        cartService.makeOrder(selectedAddressId);
        cartService.clearCart();
        return "Order was created successfully";
    }

    @DeleteMapping("/cart/{id}/deleteItem")
    @ResponseBody
    public String deleteItem(@PathVariable Long id) {
        log.info("Put request for resetting quantity");
        cartService.removeFromCart(id);
        return "Item was deleted successfully";
    }


}
