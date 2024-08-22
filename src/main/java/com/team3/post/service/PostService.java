package com.team3.post.service;

import com.team3.post.entity.PostDto;
import com.team3.post.entity.PostEntity;
import com.team3.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    //create
    public PostEntity createPost(PostDto postDto){

        PostEntity newPost = new PostEntity(
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getUserId(),
                postDto.getBoardId()
        );

        postRepository.save(newPost);

        return newPost;
    }
}
