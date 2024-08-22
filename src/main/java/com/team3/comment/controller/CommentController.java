package com.team3.comment.controller;

// 컨트롤러 관련 코드 작성 (Spring MVC 사용 시)
// @RestController
// @RequestMapping("/comments")
// public class CommentController { }

import ch.qos.logback.core.model.Model;
import com.team3.comment.entity.Comment;
import com.team3.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // GET /comments
    @GetMapping
    public String getAllComments(Model model) {
        List<Comment> comments = commentService.getAllComments();
        model.addText("comments");
        return "comment/listComments";  // 댓글 목록 페이지로 이동
    }

    // GET /comments/{id}
    @GetMapping("/{id}")
    public String getCommentById(@PathVariable("id") Integer id, Model model) {
        try {
            Comment comment = commentService.getCommentById(id);
            model.addText("comment");
            return "comment/viewComment";  // 댓글 상세 페이지로 이동
        } catch (NoSuchElementException e) {
            return "error/404";  // 댓글이 없을 때 404 페이지로 이동
        }
    }

    // GET /comments/edit/{id}
    @GetMapping("/edit/{id}")
    public String showEditCommentForm(@PathVariable("id") Integer id, Model model) {
        try {
            Comment comment = commentService.getCommentById(id);
            model.addText("comment");
            return "comment/editComment";  // 댓글 수정 폼 페이지로 이동
        } catch (NoSuchElementException e) {
            return "error/404";  // 댓글이 없을 때 404 페이지로 이동
        }
    }

    // POST /comments/edit/{id}
    @PostMapping("/edit/{id}")
    public String updateComment(@PathVariable("id") Integer id,
                                @RequestParam("content") String content) {
        try {
            commentService.updateComment(id, content);
            return "redirect:/comments";  // 댓글 목록 페이지로 리다이렉트
        } catch (NoSuchElementException e) {
            return "error/404";  // 댓글이 없을 때 404 페이지로 이동
        }
    }

    // POST /comments/delete/{id}
    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") Integer id) {
        try {
            commentService.deleteComment(id);
            return "redirect:/comments";  // 댓글 목록 페이지로 리다이렉트
        } catch (NoSuchElementException e) {
            return "error/404";  // 댓글이 없을 때 404 페이지로 이동
        }
    }
}