package com.team3.post.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private Integer views;
    private Integer userId;
    private Integer boardId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
