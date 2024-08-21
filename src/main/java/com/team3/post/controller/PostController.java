package com.team3.post.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {

    @GetMapping
    public String postList(){

        return "post/postList";
    }

    @GetMapping("/create")
    public String createPost(){
        return "post/post_create";
    }

    @GetMapping("/modify")
    public String modifyPost(){
        return "post/post_modify";
    }
}
