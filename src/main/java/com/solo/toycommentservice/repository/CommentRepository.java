package com.solo.toycommentservice.repository;

import com.solo.toycommentservice.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByBoardId(Long id);
}
