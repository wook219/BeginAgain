package com.team3.user.service;

import com.team3.user.entity.User;
import com.team3.user.entity.UserLoginDto;
import com.team3.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User login(UserLoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user;
    }
}