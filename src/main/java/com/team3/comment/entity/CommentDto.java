package com.team3.comment.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Getter
@Setter
public class CommentDto {
    private Integer commentId;
    private String content;
    private Timestamp createAt;
    private Timestamp updatedAt;
    private Integer userId;
    private String nickname;
    private Integer postId;
}
