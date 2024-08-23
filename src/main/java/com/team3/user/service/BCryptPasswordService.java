package com.team3.user.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BCryptPasswordService {

    // 비밀번호 암호화
    public String encrypt(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(10));
    }

    // 비밀번호 매칭 여부 확인
    public boolean matches(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}