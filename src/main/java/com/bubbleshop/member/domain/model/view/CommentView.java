package com.bubbleshop.member.domain.model.view;

import com.bubbleshop.member.domain.model.aggregate.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentView {
    private String commentNo;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy; // 작성자

    public CommentView(Comment comment) {
        this.commentNo = comment.getCommentNo();
        this.content = comment.getCommentContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
        this.createdBy = comment.getCreatedBy();
    }
}
