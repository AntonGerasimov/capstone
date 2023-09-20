package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        model.addAttribute("cartItems", cart.getCart());
        model.addAttribute("totalPrice", totalPrice);

        return "cart/cart"; // Return the Thymeleaf template name
    }

    @GetMapping("/cart/add")
    public String test(){
        log.info("GET REQUEST");
        return "/";
    }

    @PostMapping("/cart/{id}/add")
    @ResponseBody
    public String addItemToCart(@PathVariable Long id){
        log.info("Post request for adding item to cart");
        cartService.addToCart(id);
        return "Dish added to cart successfully";
    }

    @PostMapping("/cart/{id}/remove")
    @ResponseBody
    public String removeItemFromCart(@PathVariable Long id){
        log.info("Post request for adding item to cart");
        cartService.removeFromCart(id);
        return "Dish added to cart successfully";
    }

}
