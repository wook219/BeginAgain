package com.team3.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private Integer views;
    private Integer userId;
    private Integer boardId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public PostDto(PostEntity postEntity) {
        this.postId = postEntity.getPostId();
        this.title = postEntity.getTitle();
        this.content = postEntity.getContent();
        this.views = postEntity.getViews();
        this.userId = postEntity.getUserId();
        this.boardId = postEntity.getBoardId();
        this.createdAt = postEntity.getCreatedAt();
        this.updatedAt = postEntity.getUpdatedAt();
    }
}




