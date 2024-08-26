package com.team3.user.controller;

import com.team3.user.entity.MyPageDto;
import com.team3.user.exception.NicknameExistsException;
import com.team3.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserController {

    private final UserServiceImpl userService;

    // 회원 정보 폼
    @GetMapping
    public String myPageForm(HttpSession session, Model model) {
        Integer sessionUserId = (Integer) session.getAttribute("userId");

        if (sessionUserId == null) {
            return "redirect:/login";
        }

        MyPageDto userMyPageDto = userService.getMyPageById(sessionUserId);

        model.addAttribute("userMyPageDto", userMyPageDto);
        return "mypage";
    }

    // 회원 정보 수정 처리
    @PostMapping("/update")
    public String updateMyPage(HttpSession session,
                               @RequestParam("nickname") String newNickname,
                               Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        try {
            userService.updateNickname(userId, newNickname);
        } catch (NicknameExistsException e) {
            model.addAttribute("nicknameError", e.getMessage());
            MyPageDto userMyPageDto = userService.getMyPageById(userId);
            model.addAttribute("userMyPageDto", userMyPageDto);
            return "mypage";
        }
        return "redirect:/mypage";
    }
    // 회원 탈퇴 처리
    @PostMapping("/delete")
    public String deleteMyPage(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        userService.deleteUserById(userId);
        session.invalidate();
        return "redirect:/api/board";
    }
}