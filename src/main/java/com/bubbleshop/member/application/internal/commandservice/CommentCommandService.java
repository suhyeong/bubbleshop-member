package com.bubbleshop.member.application.internal.commandservice;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.domain.command.CreateCommentCommand;
import com.bubbleshop.member.domain.command.UpdateCommentCommand;
import com.bubbleshop.member.domain.constant.HistoryType;
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
        // 코멘트 업데이트 히스토리 내역 추가
        comment.addCommentHistory(HistoryType.UPDATED);
        return commentRepository.save(comment);
    }

    public Comment createComment(CreateCommentCommand command) {
        Comment comment = new Comment(command);
        return commentRepository.save(comment);
    }
}
