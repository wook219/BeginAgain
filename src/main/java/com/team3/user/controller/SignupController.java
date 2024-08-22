package com.team3.user.controller;

import com.team3.user.entity.UserLoginDto;
import com.team3.user.entity.UserSignupDto;
import com.team3.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userSignupDto", new UserSignupDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userSignupDto") @Valid UserSignupDto userSignupDto, BindingResult bindingResult, Model model) {

        // 비밀번호 확인 로직
        if (!userSignupDto.isPasswordConfirmed()) {
            bindingResult.rejectValue("passwordConfirm", "error.userSignupDto", "비밀번호가 일치하지 않습니다.");
        }

        // 유효성 검사 실패 시 다시 회원가입 페이지
        if (bindingResult.hasErrors()) {
            model.addAttribute("userSignupDto", userSignupDto); // 모델에 userSignupDto 추가
            return "signup";
        }

//        // 유효성 검사 실패 시 다시 회원가입 페이지
//        if (bindingResult.hasErrors()) {
//            return "signup";
//        }
//            userService.signup(userSignupDto);
//            return "redirect:/login"; // 회원가입 성공 시 로그인 페이지로 리디렉션
        try {
            userService.signup(userSignupDto);
            return "redirect:/login"; // 회원가입 성공 시 로그인 페이지로 리디렉션
        } catch (IllegalArgumentException e) {
            model.addAttribute("signupError", e.getMessage()); // 회원가입 실패 이유
            model.addAttribute("userSignupDto", userSignupDto); // 입력 데이터 유지
            return "signup";
        }
    }
}