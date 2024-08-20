package com.team3.user.controller;

import com.team3.user.entity.UserLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/signup")
    public String loginForm(Model model) {
        model.addAttribute("user", new UserLoginDto());
        return "signup";
    }
}
