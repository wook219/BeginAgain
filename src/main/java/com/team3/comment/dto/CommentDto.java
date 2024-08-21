package com.team3.comment.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private String authorUsername;

    // 생성자, Getter, Setter 등 기본 메서드들
}