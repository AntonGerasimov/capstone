package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.service.DishService;
import com.gerasimov.capstone.service.OrderService;
import com.gerasimov.capstone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@AllArgsConstructor
public class CartController {

    private static final int PAGE_SIZE = 2;

    private CartService cartService;
    private OrderService orderService;
    private DishService dishService;

    @GetMapping("/")
    public String viewMenu(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int currentPage,
            @RequestParam(name = "size", defaultValue = "2") int pageSize) {

        Page<String> menuPage = dishService.findPaginatedMenuCategories(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("menuPage", menuPage);
        model.addAttribute("categoryImages", dishService.findCategoryImageMap());

        int totalPages = menuPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "menu";
    }

    @GetMapping("/menu/{category}")
    public String viewCategory(
            @PathVariable String category,
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int currentPage,
            @RequestParam(name = "size", defaultValue = "2") int pageSize,
            @RequestParam(value = "minPrice", defaultValue = "0.0") double minPrice,
            @RequestParam(value = "maxPrice", defaultValue = "1000.0") double maxPrice

    ) {

        Page<DishDto> menuPage = dishService.findPaginatedCategoryItems(
                PageRequest.of(currentPage - 1, pageSize),
                category, minPrice, maxPrice
        );

        model.addAttribute("menuPage", menuPage);
        model.addAttribute("dishQuantityMap", cartService.createDishQuantityMap());
        model.addAttribute("category", category);
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

        return "dishes/menu-category";
    }


    @GetMapping("/cart")
    public String viewCart(Model model) {

        model.addAttribute("cartItems", cartService.getCart());
        model.addAttribute("totalPrice", cartService.calculateTotalPrice());

        return "cart/cart"; // Return the Thymeleaf template name
    }

    @GetMapping("/updateCartCount")
    @ResponseBody
    public int updateCartCount() {
        return cartService.getNumberOfItemsInCart();
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
    public void addItemToCart(@PathVariable Long dishId, HttpServletResponse response, Authentication authentication) {

        if ((authentication == null) || (!authentication.isAuthenticated())) {
            // User is not authenticated, so set a 403 status code
            log.info("User tries to add to cart, but is not logged");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Set the status code to 403
            return;
        }
        log.info("Post request for adding item to cart");
        cartService.addToCart(dishId);
    }

    @PutMapping("/cart/{id}/remove")
    @ResponseBody
    public String removeItemFromCart(@PathVariable Long id) {
        log.info("Put request for removing item to cart");
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
