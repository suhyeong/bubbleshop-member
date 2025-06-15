package com.bubbleshop.member.domain.model.aggregate;

import com.bubbleshop.member.domain.model.entity.TimeEntity;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serial;

@Entity
@Table(name = "authority_master")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class Authority extends TimeEntity {
    @Serial
    private static final long serialVersionUID = 2507582695070598284L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;

    private String role;
}
