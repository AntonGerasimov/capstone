package com.gerasimov.capstone.dbclasses.controllers;

import com.gerasimov.capstone.dbclasses.util.AuthenticationResult;
import com.gerasimov.capstone.dbclasses.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@AllArgsConstructor
public class RootController {

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(){
        log.info("Logout");
        return "redirect:/";
    }


}
