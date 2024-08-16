package com.backend.ourstory.user.dto.Request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserProfileUpdateDto {
    private String name;
    private String email;
    private String nickname;
    private String profile_image_url;
    private String phone_number;

}
