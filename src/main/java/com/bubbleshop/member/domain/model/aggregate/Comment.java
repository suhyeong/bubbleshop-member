package com.bubbleshop.member.domain.model.aggregate;

import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.member.domain.command.CreateCommentCommand;
import com.bubbleshop.member.domain.constant.CommentType;
import com.bubbleshop.member.domain.constant.HistoryType;
import com.bubbleshop.member.domain.model.converter.CommentTypeConverter;
import com.bubbleshop.member.domain.model.entity.CommentHistory;
import com.bubbleshop.member.domain.model.entity.ReviewImage;
import com.bubbleshop.member.domain.model.entity.TimeEntity;
import com.bubbleshop.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.bubbleshop.util.DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS;

@Entity
@Table(name = "comment_master")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Comment extends TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -3643508812901384196L;

    @Id
    @Description("코멘트 번호")
    @Column(name = "cmnt_no")
    private String commentNo; // 규칙 C + YYYYMMDDHHMMSSSSS

    @Description("코멘트 타입")
    @Column(name = "cmnt_type", updatable = false)
    @Convert(converter = CommentTypeConverter.class)
    private CommentType commentType;

    @Description("타겟 번호")
    @Column(name = "target_no", updatable = false)
    private String targetNo; // reviewNo or qnaNo

    @Description("코멘트 내용")
    @Column(name = "cmnt_cont")
    private String commentContent;

    @Description("생성자")
    @Column(name = "crt_by")
    private String createdBy;

    @OneToMany(mappedBy = "comment", targetEntity = CommentHistory.class, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"comment"})
    private List<CommentHistory> histories = new ArrayList<>();

    public Comment(CreateCommentCommand command) {
        this.commentNo = StaticValues.Prefix.COMMENT_NO_PREFIX + DateTimeUtils.convertDateTimeToString(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS, LocalDateTime.now());
        this.commentType = CommentType.find(command.getCommentType());
        this.targetNo = command.getTargetNo();
        this.commentContent = command.getContent();
        this.createdBy = command.getCreatedBy();
        this.addCommentHistory(HistoryType.CREATED);
    }

    public void updateContent(String content) {
        this.commentContent = content;
    }

    public void addCommentHistory(HistoryType historyType) {
        this.histories.add(new CommentHistory(this, this.histories.size() + 1, historyType));
    }
}
