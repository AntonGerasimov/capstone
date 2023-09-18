package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.service.DishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class CartController {
    private final Cart cart;
    private final CartService cartService;

    @GetMapping("/cart")
    public String viewCart(Model model) {
//        List<DishDto> cartItems = dishService.getCartItems(httpSession); // Retrieve cart items from your service

        List<DishDto> cartItems = new ArrayList<>();
        // Calculate the total price of items in the cart
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getPrice())
                .sum();

        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("totalPrice", totalPrice);

        return "cart/cart"; // Return the Thymeleaf template name
    }

    @PostMapping("/cart/add")
    public String addItemToCart(@RequestParam("itemId") Long dishId, Model model){
        log.info("Post request for adding item to cart");
        cartService.addToCart(dishId, cart);
//        String responseMessage = "Item added to cart successfully.";
//        ModelAndView cartMV = new ModelAndView();
        return "menu";
    }
}
