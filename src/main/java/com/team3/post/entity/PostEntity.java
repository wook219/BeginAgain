package com.team3.post.entity;

import com.team3.board.BoardEntity;
import com.team3.global.entity.BaseTimeEntity;
import com.team3.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostPhotoEntity> postPhoto = new ArrayList<>();


    //게시글 create를 위한 생성자
    public PostEntity (String title, String content, User user, BoardEntity board) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.board = board;
    }
}
