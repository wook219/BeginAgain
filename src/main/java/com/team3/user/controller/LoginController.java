package com.team3.user.controller;

import com.team3.user.entity.User;
import com.team3.user.entity.UserLoginDto;
import com.team3.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto, HttpSession session, Model model) {
        try {
            User loginResult = userService.login(userLoginDto);
            session.setAttribute("email", loginResult.getEmail());
            return "home"; // 로그인 성공
        } catch (IllegalArgumentException e) {
            model.addAttribute("loginError", "Invalid email or password"); // 에러 메시지 설정
            return "login"; // 로그인 실패
        }
    }
}