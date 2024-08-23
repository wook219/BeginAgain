package com.team3.user.service;

import com.team3.user.entity.RoleType;
import com.team3.user.entity.User;
import com.team3.user.entity.UserLoginDto;
import com.team3.user.entity.UserSignupDto;
import com.team3.user.exception.*;
import com.team3.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordService passwordEncoder;

    // 로그인
    public User login(UserLoginDto loginDto) {
        // 이메일로 사용자를 조회, 없으면 예외 처리
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(EmailNotFoundException::new);
        // 비밀번호가 일치하지 않으면 예외 처리
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }
        return user;
    }

    // 회원가입
    @Transactional
    public User signup(UserSignupDto signupDto) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(signupDto.getEmail()).isPresent()) {
            throw new EmailExistsException();
        }

        // 닉네임 중복 체크
        if (userRepository.findByNickname(signupDto.getNickname()).isPresent()) {
            throw new NicknameExistsException();
        }

        String eneryptedPassword = passwordEncoder.encrypt(signupDto.getPassword());

        // 비밀번호 중복 체크
        if (userRepository.findAll().stream()
                .anyMatch(user -> passwordEncoder.matches(signupDto.getPassword(), user.getPassword()))) {
            throw new PasswordExistsException();
        }

        // 회원 저장
        User user = User.builder()
                .email(signupDto.getEmail())
                .password(eneryptedPassword)
                .username(signupDto.getUsername())
                .nickname(signupDto.getNickname())
                .role(RoleType.USER)  // 기본 역할 (User)
                .build();

        return userRepository.save(user);
    }
}
