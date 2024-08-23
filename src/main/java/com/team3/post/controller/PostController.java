package com.team3.post.controller;


import com.team3.board.BoardEntity;
import com.team3.board.BoardService;
import com.team3.post.entity.PostDto;
import com.team3.post.entity.PostEntity;
import com.team3.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private BoardService boardService;

    @GetMapping("/{boardId}")
    public String postList(@PathVariable("boardId") Integer boardId, Model m){
        List<PostDto> posts = postService.getPostsByBoardId(boardId);
        BoardEntity board = boardService.getBoard(boardId);

        String boardTitle = board.getTitle();

        m.addAttribute("posts",posts);
        m.addAttribute("boardId", boardId);
        m.addAttribute("boardTitle", boardTitle);

        return "post/postList";
    }

    @GetMapping("/postdetail/{postId}")
    public String postdetail(@PathVariable("postId") Integer postId, Model m){
        PostEntity post = postService.getPostByPostId(postId);
        Integer boardId = post.getBoardId();

        m.addAttribute("boardId", boardId);
        m.addAttribute("post", post);

        return "post/post";
    }

    @GetMapping("/create/{boardId}")
    public String createPostForm(@PathVariable("boardId") Integer boardId, Model m){
        m.addAttribute("boardId",boardId);
        return "post/post_create";
    }

    @PostMapping("/create")
    public String createPost(@RequestParam("boardId") Integer boardId,
                                PostDto postDto,
                             HttpSession session){

        Integer userId = (Integer)session.getAttribute("userId");
        postDto.setUserId(userId);

        postDto.setBoardId(boardId);

        postService.createPost(postDto);

        return "redirect:/post/" + boardId;
    }

    @GetMapping("/modify/{postId}")
    public String modifyForm(@PathVariable("postId") Integer postId, Model m){
        PostEntity post = postService.getPostByPostId(postId);

        m.addAttribute("post", post);
        return "post/post_modify";
    }

    @PostMapping("/modify/{postId}")
    public String modifyPost(@PathVariable("postId") Integer postId,
                             PostDto postDto){
        postService.modifyPost(postDto);

        return "redirect:/post/postdetail/"+postId;
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable("postId") Integer postId,
                             @RequestParam("boardId") Integer boardId){

        postService.deletePost(postId);

        return "redirect:/post/" + boardId;
    }
}
