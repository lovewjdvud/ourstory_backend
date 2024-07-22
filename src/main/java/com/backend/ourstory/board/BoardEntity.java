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
public class BoardEntity extends BaseEntity {

    // @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
    private int comment_count;
    private int like_count;

    @Column(nullable = false)
    private String tag_type;

    @Column(nullable = false)
    private long user_id;
    @Column(nullable = false)
    private String user_nickname;
    @Column(nullable = false)
    private String user_image_url;
//
//    @ManyToOne
//    @JoinColumn(name = "tag_id", nullable = false)
//    private Tag tag;

}
