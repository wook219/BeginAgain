package com.team3.global.exception;

import com.team3.user.exception.EmailNotFoundException;
import com.team3.user.exception.IncorrectPasswordException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    public String handleEmailNotFoundException(EmailNotFoundException ex, Model model) {
        model.addAttribute("emailError", ex.getErrorCode().getMessage());
        return "login";
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public String handleIncorrectPasswordException(IncorrectPasswordException ex, Model model) {
        model.addAttribute("passwordError", ex.getErrorCode().getMessage());
        return "login";
    }
}
