package com.backend.ourstory.comment;

import com.backend.ourstory.board.BoardEntity;
import com.backend.ourstory.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long boardId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private long user_id;
    @Column(nullable = false)
    private String user_nick_name;
    @Column(nullable = false)
    private String user_image_url;

}
