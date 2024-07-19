package com.backend.ourstory.like;

import com.backend.ourstory.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`like`") // 예약어 충돌을 피하기 위해 backtick 사용
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // 기본 키 필드 이름을 일반적으로 id로 설정

    @Column(nullable = false)
    private long b_id;

    @Column(nullable = false)
    private String user_nickName;

    private String is_like;
}
