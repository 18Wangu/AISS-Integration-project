package aiss.videominer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aiss.videominer.model.Comment;
import aiss.videominer.service.*;

@RestController
@RequestMapping("/videominer/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/")
    public List<Comment> getAllComment() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable String id) {
        return commentService.getCommentById(id);
    }

}
