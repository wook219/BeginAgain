package com.team3.post.entity;

import com.team3.board.BoardEntity;
import com.team3.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 2000, nullable = false)
    private String content;

    @Column(name = "views", nullable = false)
    private Integer views = 0;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    //    @ManyToOne
    //    @JoinColumn(name = "board_id", nullable = false)
    //    private BoardEntity board;
    @Column(name = "board_id", nullable = false)
    private Integer boardId;

    //게시글 create를 위한 생성자
    public PostEntity(String title, String content, Integer userId, Integer boardId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.boardId = boardId;
    }

    @PreUpdate
    public void setUpdatedAt(){
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PrePersist
    public void setCreatedAt(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
