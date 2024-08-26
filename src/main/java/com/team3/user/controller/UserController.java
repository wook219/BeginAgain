package com.team3.user.controller;

import com.team3.user.entity.MyPageDto;
import com.team3.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage")
    public String myPageForm(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login"; // 세션에 사용자 ID가 없으면 로그인 페이지로 리다이렉트
        }

        MyPageDto userMyPageDto = userService.getUserById(userId);

        model.addAttribute("userMyPageDto", userMyPageDto);
        return "mypage";
    }
}
