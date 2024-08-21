package com.team3.comment.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private User author;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자, Getter, Setter 등 기본 메서드들
}
