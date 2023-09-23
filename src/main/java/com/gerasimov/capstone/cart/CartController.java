package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
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

    @GetMapping("/")
    public String showMainPage(Model model) {

        List<DishDto> menuItems = cartService.getMenu();
        List<DishDto> hotSales = cartService.getHotSales();

        // Group the menu items by category
        Map<String, List<DishDto>> menuItemsByCategory = menuItems.stream()
                .collect(Collectors.groupingBy(DishDto::getCategory));

        Map<String, Integer> dishQuantityMap = cartService.createDishQuantityMap();
        model.addAttribute("dishQuantityMap", dishQuantityMap);
        model.addAttribute("menuItemsByCategory", menuItemsByCategory);
        model.addAttribute("hotSales", hotSales);
        return "index";
    }

    @GetMapping("/menu")
    public String getAllDishes(Model model) {
        List<DishDto> menuItems = cartService.getMenu();

        // Group the menu items by category
        Map<String, List<DishDto>> menuItemsByCategory = menuItems.stream()
                .collect(Collectors.groupingBy(DishDto::getCategory));

        model.addAttribute("menuItemsByCategory", menuItemsByCategory);
        Map<String, Integer> dishQuantityMap = cartService.createDishQuantityMap();
        model.addAttribute("dishQuantityMap", dishQuantityMap);
        return "menu";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
//        List<DishDto> cartItems = dishService.getCartItems(httpSession); // Retrieve cart items from your service

        List<DishDto> cartItems = new ArrayList<>();
        // Calculate the total price of items in the cart
        double totalPrice = cartItems.stream()
                .mapToDouble(DishDto::getPrice)
                .sum();

        model.addAttribute("cartItems", cartService.getCart());
        model.addAttribute("totalPrice", totalPrice);

        return "cart/cart"; // Return the Thymeleaf template name
    }

    @GetMapping("/checkout")
    public String viewCheckout(Model model) {
        model.addAttribute("cartItems", cartService.getCart());
        model.addAttribute("addresses", cartService.findAddressesForUser());
        return "cart/checkout";
    }

    @PostMapping("/cart/{id}/add")
    @PreAuthorize("hasAnyRole('*')")
    @ResponseBody
    public String addItemToCart(@PathVariable Long id, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication == null) || (!authentication.isAuthenticated())) {
            // User is not authenticated, so set a 403 status code and return an error message.
            log.info("Not authenticated user");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Set the status code to 403
            return "You do not have permission to perform this action.";
        }
        log.info("Post request for adding item to cart");
        cartService.addToCart(id);
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
