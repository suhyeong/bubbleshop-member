package com.bubbleshop.member.domain.model.aggregate;

import com.bubbleshop.member.domain.model.entity.TimeEntity;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

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
    private String commentNo;

    @Description("코멘트 타입")
    @Column(name = "cmnt_type")
    private String commentType;

    @Description("타겟 번호")
    @Column(name = "target_no")
    private String targetNo; // reviewNo or qnaNo

    @Description("코멘트 내용")
    @Column(name = "cmnt_cont")
    private String commentContent;

    @Description("생성자")
    @Column(name = "crt_by")
    private String createdBy;

}
