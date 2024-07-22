package com.backend.ourstory.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

// 필요한 유저정보, 게시글 닉네임, 게시글 유저 아이디, 유저 프로필 url
public class BoardAddDto {

    @NotBlank(message = "title 은 필수 입력 항목입니다.")
    private String title;

    @NotBlank(message = "content는 필수 입력 항목입니다.")
    private String content;

    @Schema(description = "전체(A), 위로 받고 싶은 일(B), 연애(C), 친구 (C), 학교 (D),  직장(E)", example = "T")
    @NotBlank(message = "tag_type은 필수 입력 항목입니다.")
    private String tag_type;

    @NotBlank(message = "유저 아이디는 필수 입력 항목입니다.")
    private long user_id;

    @NotBlank(message = "유저 닉네임은 필수 입력 항목입니다.")
    private String user_nickname;

    @NotBlank(message = "유저 이미지 url은 필수 입력 항목입니다.")
    private String user_profile_image;


    @Builder
    public BoardAddDto(String title, String content, String tag_type, long user_id, String user_nickname, String user_profile_image) {
        this.title = title;
        this.content = content;
        this.tag_type = tag_type;
        this.user_id = user_id;
        this.user_nickname = user_nickname;
        this.user_profile_image = user_profile_image;
    }
}
