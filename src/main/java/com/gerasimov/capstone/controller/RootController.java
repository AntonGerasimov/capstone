package com.gerasimov.capstone.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@Slf4j
@AllArgsConstructor
public class RootController {

    @GetMapping("/")
    public String showMainPage() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        log.info("Logout");
        return "redirect:/";
    }
}
