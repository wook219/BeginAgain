package com.team3.user.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class PlainPasswordEncoder implements PasswordEncoder {

    @Override
    public String encrypt(String rawPassword) {
        return rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return rawPassword.equals(encryptedPassword);
    }
}