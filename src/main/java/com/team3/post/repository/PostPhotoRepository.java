package com.team3.post.repository;

import com.team3.post.entity.PostPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostPhotoRepository extends JpaRepository<PostPhotoEntity,Integer> {
    List<PostPhotoEntity> findByPost_PostId(Integer postId);
}
