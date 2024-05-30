package com.solo.toycommentservice.controller;

import com.solo.toycommentservice.entity.CommentEntity;
import com.solo.toycommentservice.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }

    @GetMapping("/{boardId}")
    public List<CommentEntity> getComments(@PathVariable long boardId) {

        return commentService.getAllComments(boardId);
    }

    @PostMapping("")
    public ResponseEntity<?> addComment(@RequestBody CommentEntity commentEntity) {
        return null;
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentEntity commentEntity, @PathVariable long commentId) {
        return null;
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable long commentId) {
        return null;
    }

}
