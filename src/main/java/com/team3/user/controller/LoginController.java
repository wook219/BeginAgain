package com.team3.user.controller;

import com.team3.user.entity.User;
import com.team3.user.entity.UserLoginDto;
import com.team3.user.entity.UserSignupDto;
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
    public String loginForm(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto, HttpSession session, Model model) {
        try {
            User loginResult = userService.login(userLoginDto);
            session.setAttribute("email", loginResult.getEmail()); // 이메일을 세션에 저장
            return "redirect:/listBoards";
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("이메일이 존재하지 않습니다.")) {
                model.addAttribute("emailError", "존재하지 않는 계정입니다.");
            } else if (e.getMessage().contains("비밀번호가 맞지 않습니다.")) {
                model.addAttribute("passwordError", "비밀번호가 맞지 않습니다.");
            }
            model.addAttribute("userLoginDto", userLoginDto); // 입력 데이터 유지
            return "login";
        }
    }
}