package com.example.employee.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/** Turns unexpected exceptions into a friendly error page. */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAny(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "Something went wrong: " + ex.getMessage());
        return mav;
    }
}
