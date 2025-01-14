package com.bubbleshop.member.domain.model.entity;


import jdk.jfr.Description;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {
    @Description("생성 일시")
    @Column(name = "crt_dt", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Description("수정 일시")
    @Column(name = "chn_dt")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
