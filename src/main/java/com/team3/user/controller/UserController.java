package com.team3.user.controller;

import com.team3.user.entity.MyPageDto;
import com.team3.user.exception.NicknameExistsException;
import com.team3.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 마이페이지 폼
    @GetMapping("/mypage/{id}")
    public String myPageForm(@PathVariable("id") Integer userId,HttpSession session, Model model) {
        Integer sessionUserId = (Integer) session.getAttribute("userId");

        if (sessionUserId == null || !sessionUserId.equals(userId)) {
            return "redirect:/login";
        }

        MyPageDto userMyPageDto = userService.getUserById(userId);

        model.addAttribute("userMyPageDto", userMyPageDto);
        return "mypage";
    }

    // 닉네임 수정 처리
    @PostMapping("/mypage/update/{id}")
    public String updateMyPage(@PathVariable("id") Integer userId,
                               @RequestParam("nickname") String newNickname,
                               Model model) {
        try {
            userService.updateNickname(userId, newNickname);
        } catch (NicknameExistsException e) {
            model.addAttribute("nicknameError", e.getMessage());
            MyPageDto userMyPageDto = userService.getUserById(userId);
            model.addAttribute("userMyPageDto", userMyPageDto);
            return "mypage";
        }
        return "redirect:/mypage/" + userId;
    }

    @PostMapping("/mypage/delete/{id}")
    public String deleteMyPage(@PathVariable("id") Integer userId, HttpSession session) {
        Integer sessionUserId = (Integer) session.getAttribute("userId");

        if (sessionUserId == null || !sessionUserId.equals(userId)) {
            return "redirect:/login";
        }

        userService.deleteUserById(userId);
        session.invalidate();
        return "redirect:/api/board";
    }
}