package com.team3.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private Integer views;
    private Integer userId;
    private String nickname;
    private Integer boardId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public PostDto(PostEntity postEntity) {
        this.postId = postEntity.getPostId();
        this.title = postEntity.getTitle();
        this.content = postEntity.getContent();
        this.views = postEntity.getViews();
        this.userId = postEntity.getUser().getId();
        this.nickname = postEntity.getUser().getNickname();
        this.boardId = postEntity.getBoard().getBoardId();
        this.createdAt = postEntity.getCreatedAt();
        this.updatedAt = postEntity.getUpdatedAt();
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", boardId=" + boardId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}




