package com.backend.ourstory.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class CommentAddDto {

    @NotBlank(message = "boardId 필수 입력 항목입니다.")
    private long boardId;

    @NotBlank(message = "content는 필수 입력 항목입니다.")
    private String content;

//    @NotBlank(message = "유저 아이디는 필수 입력 항목입니다.")
//    private long user_id;
//
//    @NotBlank(message = "유저 닉네임은 필수 입력 항목입니다.")
//    private String user_nickname;
//
//    @NotBlank(message = "유저 이미지 url은 필수 입력 항목입니다.")
//    private String user_profile_image;


    @Builder
    public CommentAddDto(long boardId, String content) {
        this.boardId = boardId;
        this.content = content;
//        this.user_id = user_id;
//        this.user_nickname = user_nickname;
//        this.user_profile_image = user_profile_image;
    }

}
