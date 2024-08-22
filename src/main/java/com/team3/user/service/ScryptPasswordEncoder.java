package com.team3.user.service;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("default")
@Component
public class ScryptPasswordEncoder implements PasswordEncoder {

    private static final SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
            16,
            8,
            1,
            32,
            64);

    @Override
    public String encrypt(String rawPassword){
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encrpytedPassword) {
        return encoder.matches(rawPassword, encrpytedPassword);
    }
}