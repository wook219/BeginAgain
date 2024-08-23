package com.team3.user.entity;

import com.team3.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length=200)
    private String email;

    @Column(name = "password", nullable = false, length=200)
    private String password;

    @Column(name = "username", nullable = false, length=30)
    private String username;

    @Column(name = "nickname", nullable = false, length=30)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role; // ADMIN, USER

    @Builder
    public User(String email, String password, String username, String nickname, RoleType role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
    }
}