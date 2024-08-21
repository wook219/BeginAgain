package com.team3.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

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
    private RoleType role;

    @Builder
    public User(String email, String password, String username, String nickname, RoleType role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
    }
}