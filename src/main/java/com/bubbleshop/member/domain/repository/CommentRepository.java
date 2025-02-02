package com.bubbleshop.member.domain.repository;

import com.bubbleshop.member.domain.constant.CommentType;
import com.bubbleshop.member.domain.model.aggregate.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findCommentsByTargetNoAndCommentType(String targetNo, CommentType commentType);
}
