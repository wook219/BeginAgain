package com.team3.board;

import com.team3.post.entity.PostEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
@Data
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Integer boardId;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 200, nullable = false)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private UserEntity user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @Column(name = "is_deleted", nullable = true)
    private boolean isDeleted;

    // 엔티티가 저장되기 전에 호출됨
    @PrePersist
    @PreUpdate
    public void updateIsDeleted() {
        this.isDeleted = this.deletedAt != null;
    }


//    //게시글과 맵핍하기 위한 게시글 엔티티
//    @OneToMany(mappedBy = "board")
//    private List<PostEntity> posts;
}
