package com.backend.ourstory.user.dto.Request;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
//    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;

    @NotNull()
    private int phoneNumber;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

    private String profileImageUrl;


    @Builder
    public UserDto(String email, String name, String nickname, int phoneNumber, String password, String profileImageUrl) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
    }
}
