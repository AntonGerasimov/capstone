package com.gerasimov.capstone.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(RestaurantException.class)
    public ModelAndView handleRestaurantException(HttpServletRequest request, RestaurantException e, Model model) {
        log.error("Restaurant error", e);
        model.addAttribute("error", e.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("Restaurant exception", e);
        request.setAttribute("errorMessage", "Wrong password");
        return modelAndView;
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleOtherException(HttpServletRequest request, Throwable e) {
        log.error("Error", e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", e);
        return modelAndView;
    }
}