package com.team3.user.service;

import com.team3.user.entity.RoleType;
import com.team3.user.entity.User;
import com.team3.user.entity.UserLoginDto;
import com.team3.user.entity.UserSignupDto;
import com.team3.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User login(UserLoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        return user;
    }

    @Transactional
    public User signup(UserSignupDto signupDto) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(signupDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 닉네임 중복 체크
        if (userRepository.findByNickname(signupDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 비밀번호 중복 체크
        if (isPasswordDuplicate(signupDto.getPassword())) {
            throw new IllegalArgumentException("이미 사용 중인 비밀번호입니다.");
        }

        // User 엔티티 생성 및 저장
        User user = User.builder()
                .email(signupDto.getEmail())
                .password(signupDto.getPassword())  // 암호화 필요
                .username(signupDto.getUsername())
                .nickname(signupDto.getNickname())
                .role(RoleType.USER)  // 기본 역할 (User)
                .build();

        return userRepository.save(user);
    }

    // 비밀번호 중복 체크
    private boolean isPasswordDuplicate(String password) {
        return userRepository.existsByPassword(password);
    }
}