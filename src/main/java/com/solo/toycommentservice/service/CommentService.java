package com.solo.toycommentservice.service;

import com.solo.toycommentservice.entity.CommentEntity;
import com.solo.toycommentservice.repository.CommentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {

        this.commentRepository = commentRepository;
    }

    public List<CommentEntity> getAllComments(long boardId) {
        return commentRepository.findAllByBoardId(boardId);
    }

    public ResponseEntity<?> addComment(CommentEntity commentEntity) {
        return null;
    }

    public ResponseEntity<?> updateComment(CommentEntity commentEntity, long commentId) {
        return null;
    }

    public ResponseEntity<?> deleteComment(long commentId) {
        return null;
    }
}
