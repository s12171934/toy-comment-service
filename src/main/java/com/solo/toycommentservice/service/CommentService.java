package com.solo.toycommentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solo.toycommentservice.entity.CommentEntity;
import com.solo.toycommentservice.repository.CommentRepository;
import com.solo.toycommentservice.util.PassportUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PassportUtil passportUtil;
    private final WebClient.Builder webClientBuilder;

    public CommentService(CommentRepository commentRepository, PassportUtil passportUtil, WebClient.Builder webClientBuilder) {

        this.commentRepository = commentRepository;
        this.passportUtil = new PassportUtil();
        this.webClientBuilder = webClientBuilder;
    }

    public List<CommentEntity> getAllComments(long boardId) {

        return commentRepository.findAllByBoardId(boardId);
    }

    public ResponseEntity<?> addComment(HttpServletRequest request, CommentEntity commentEntity) throws JsonProcessingException {

        //댓글 생성 시 적합한 회원인지 검사
        String passportUsername = passportUtil.getUsername(request.getHeader("passport"));

        if(!passportUsername.equals(commentEntity.getWriter())) {
            return new ResponseEntity<>("Not user matched", HttpStatus.UNAUTHORIZED);
        }

        //use-service에 있는 유저인지 확인 요청
        //board-service에 있는 게시물인지 확인 요청
        if(!checkUserAndBoard(request, commentEntity.getBoardId())) {
            return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
        }


        commentRepository.save(commentEntity);
        return new ResponseEntity<>(commentEntity, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateComment(HttpServletRequest request, CommentEntity commentEntity, long commentId) throws JsonProcessingException {

        String passportUsername = passportUtil.getUsername(request.getHeader("passport"));

        String commentOwner = commentRepository.findById(commentId).get().getWriter();

        if(!passportUsername.equals(commentOwner)) {
            return new ResponseEntity<>("Not user matched", HttpStatus.UNAUTHORIZED);
        }

        //use-service에 있는 유저인지 확인 요청
        //board-service에 있는 게시물인지 확인 요청
        if(!checkUserAndBoard(request, commentEntity.getBoardId())) {
            return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
        }

        commentEntity.setId(commentId);
        commentRepository.save(commentEntity);
        return new ResponseEntity<>(commentEntity, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteComment(HttpServletRequest request, long commentId) throws JsonProcessingException {

        String passportUsername = passportUtil.getUsername(request.getHeader("passport"));

        String commentOwner = commentRepository.findById(commentId).get().getWriter();

        if(!passportUsername.equals(commentOwner)) {
            return new ResponseEntity<>("Not user matched", HttpStatus.UNAUTHORIZED);
        }

        commentRepository.deleteById(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //이것도 카프카를 통해서 해야하는가?
    private boolean checkUserAndBoard (HttpServletRequest request, Long boardId) throws JsonProcessingException {

        Map<String, String> userMap;
        Map<String, String> boardMap;
        ObjectMapper mapper = new ObjectMapper();

        String userJson = webClientBuilder.baseUrl("http://USER-SERVICE").defaultHeader("passport", request.getHeader("passport"))
                .build().get().uri("/user").retrieve().bodyToMono(String.class).block();
        userMap = mapper.readValue(userJson, Map.class);

        if(userMap.isEmpty()) return false;

        String boardJson = webClientBuilder.baseUrl("http://BOARD-SERVICE").defaultHeader("passport", request.getHeader("passport"))
                .build().get().uri("/board/" + boardId).retrieve().bodyToMono(String.class).block();
        boardMap = mapper.readValue(boardJson, Map.class);

        if(boardMap.isEmpty()) return false;

        return true;
    }
}
