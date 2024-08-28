package com.team3.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private Integer id;
    private String content;
    private String nickname;
}