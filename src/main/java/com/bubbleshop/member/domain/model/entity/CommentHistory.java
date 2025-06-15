package com.bubbleshop.member.domain.model.entity;

import com.bubbleshop.member.domain.constant.CommentType;
import com.bubbleshop.member.domain.constant.HistoryType;
import com.bubbleshop.member.domain.model.aggregate.Comment;
import com.bubbleshop.member.domain.model.aggregate.Review;
import com.bubbleshop.member.domain.model.converter.CommentTypeConverter;
import com.bubbleshop.member.domain.model.converter.HistoryTypeConverter;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "comment_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class CommentHistory extends TimeEntity {
    @Serial
    private static final long serialVersionUID = 6213941887040648392L;

    @EmbeddedId
    private CommentHistoryId commentHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cmnt_no", insertable = false, updatable = false)
    @ToString.Exclude
    private Comment comment;

    @Description("코멘트 이력 타입")
    @Column(name = "cmnt_hist_type", updatable = false)
    @Convert(converter = HistoryTypeConverter.class)
    private HistoryType commentHistoryType;

    @Description("코멘트 타입")
    @Column(name = "cmnt_type", updatable = false)
    @Convert(converter = CommentTypeConverter.class)
    private CommentType commentType;

    @Description("타겟 번호")
    @Column(name = "target_no", updatable = false)
    private String targetNo; // reviewNo or qnaNo

    @Description("코멘트 내용")
    @Column(name = "cmnt_cont", updatable = false)
    private String commentContent;

    @Description("생성자")
    @Column(name = "crt_by", updatable = false)
    private String createdBy;

    public CommentHistory(Comment comment, int sequence, HistoryType historyType) {
        this.commentHistoryId = new CommentHistoryId(comment.getCommentNo(), sequence);
        this.commentHistoryType = historyType;
        this.commentType = comment.getCommentType();
        this.targetNo = comment.getTargetNo();
        this.commentContent = comment.getCommentContent();
        this.createdBy = comment.getCreatedBy();
    }
}
