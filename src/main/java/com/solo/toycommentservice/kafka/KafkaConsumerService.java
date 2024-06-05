package com.solo.toycommentservice.kafka;

import com.solo.toycommentservice.repository.CommentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final CommentRepository commentRepository;

    public KafkaConsumerService(CommentRepository commentRepository) {

        this.commentRepository = commentRepository;
    }

    //유저 삭제를 listen, comment 삭제
    @KafkaListener(topics = "user-delete", groupId = "user-group")
    public void listenUserDelete(String message) {

        commentRepository.deleteAllByWriter(message);
        System.out.println("Message received by Kafka: " + message);
    }

    //게시물 삭제를 listen, comment 삭제
    @KafkaListener(topics = "board-delete", groupId = "board-group")
    public void listenBoardDelete(String message) {

        commentRepository.deleteAllByBoardId(Long.parseLong(message));
        System.out.println("Message received by Kafka: " + message);
    }
}