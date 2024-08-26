package com.team3.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageDto {

    private String email;
    private String username;
    private String nickname;

    public MyPageDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }

}
