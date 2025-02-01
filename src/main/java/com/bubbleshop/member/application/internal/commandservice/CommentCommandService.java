package com.bubbleshop.member.application.internal.commandservice;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.command.UpdateCommentCommand;
import com.bubbleshop.member.domain.model.aggregate.Comment;
import com.bubbleshop.member.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCommandService {
    private final CommentRepository commentRepository;

    public Comment updateCommentInfo(UpdateCommentCommand command) {
        Comment comment = commentRepository.findById(command.getCommentNo()).orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));
        // 코멘트 내용 수정
        comment.updateContent(command.getContent());
        return commentRepository.save(comment);
    }
}
