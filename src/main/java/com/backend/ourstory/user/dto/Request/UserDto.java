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
    private String email;

    private String name;

    private String nickname;

    private String phoneNumber;


    private String password;

    private String profileImageUrl;
    private String snsType;


    @Builder
    public UserDto(String email, String name, String nickname, String phoneNumber, String password, String profileImageUrl, String snsType) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.snsType = snsType;
    }
}
