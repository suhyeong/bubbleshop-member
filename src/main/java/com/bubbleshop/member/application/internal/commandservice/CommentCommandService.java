package com.bubbleshop.member.application.internal.commandservice;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.model.aggregate.Comment;
import com.bubbleshop.member.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCommandService {
    private final CommentRepository commentRepository;

    public Comment updateCommentContents(String commentNo, String content) {
        Comment comment = commentRepository.findById(commentNo).orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));
        comment.updateContent(content);
        return commentRepository.save(comment);
    }
}
