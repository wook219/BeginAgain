package com.team3.post.entity;

import com.team3.board.BoardEntity;
import com.team3.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    // 새로운 필드: 이미지 파일 경로
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostPhotoEntity> postPhoto = new ArrayList<>();

    //게시글 create를 위한 생성자
    public PostEntity(String title, String content, Integer userId, Integer boardId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.boardId = boardId;
    }
}
