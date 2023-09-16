package com.gerasimov.capstone.controller;

import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@AllArgsConstructor
public class RootController {

    private UserService userService;

    @GetMapping("/")
    public String showMainPage() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("newUser", new UserDto());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        log.info("Logout");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String viewAfterLogin(@ModelAttribute UserDto newUser, RedirectAttributes redirectAttributes, HttpServletRequest request){
        try{
            userService.login(newUser, request);
            return "redirect:/";
        } catch (BadCredentialsException e){
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Invalid password");
            return "redirect:/login";
        } catch (RestaurantException e){
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }


}
