package com.backend.ourstory.board;

import com.backend.ourstory.common.entity.BaseEntity;
import com.backend.ourstory.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class Board extends BaseEntity {

    // @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;
    private String content;
    private int comment_count;
    private int like_count;
    private String tag_type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
//
//    @ManyToOne
//    @JoinColumn(name = "tag_id", nullable = false)
//    private Tag tag;

}
