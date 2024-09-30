package com.team3.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_photo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostPhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_photo_id")
    private Integer photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "image_path")
    private String imagePath;

    public PostPhotoEntity(PostEntity post, String imagePath){
        this.post = post;
        this.imagePath = imagePath;
    }
}
