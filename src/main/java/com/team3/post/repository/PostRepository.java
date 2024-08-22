package com.team3.post.repository;

import com.team3.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
}
