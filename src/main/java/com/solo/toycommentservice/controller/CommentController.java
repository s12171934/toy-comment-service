package com.solo.toycommentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.solo.toycommentservice.entity.CommentEntity;
import com.solo.toycommentservice.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<?> addComment(HttpServletRequest request, @RequestBody CommentEntity commentEntity) throws JsonProcessingException {

        return commentService.addComment(request, commentEntity);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(HttpServletRequest request, @RequestBody CommentEntity commentEntity, @PathVariable long commentId) throws JsonProcessingException {

        return commentService.updateComment(request, commentEntity, commentId);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(HttpServletRequest request, @PathVariable long commentId) throws JsonProcessingException {

        return commentService.deleteComment(request, commentId);
    }

    @DeleteMapping("/allByBoard/{boardId}/{boardWriter}")
    public ResponseEntity<?> deleteAllByBoard(HttpServletRequest request, @PathVariable long boardId, @PathVariable String boardWriter) throws JsonProcessingException {

        return commentService.deleteAllComments(request, boardWriter, boardId);
    }

    @DeleteMapping("/allByUser/{username}")
    public ResponseEntity<?> deleteAllByUser(HttpServletRequest request, @PathVariable String username) throws JsonProcessingException {

        return commentService.deleteAllComments(request, username, null);
    }


}
