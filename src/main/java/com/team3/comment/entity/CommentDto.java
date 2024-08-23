package com.team3.comment.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter

public class CommentDto {
    private Integer commentId;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Integer userId2;
    private Integer postId;


}
