package com.backend.ourstory.user;

import com.backend.ourstory.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ProfileImage  {

    // @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userid;

    @Column(nullable = false)
    private String imageurl;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
