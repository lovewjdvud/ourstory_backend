package com.backend.ourstory.user.dto.Response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserProfileInfoResponseDto {
    private long id;
    private String name;
    private String email;
    private String nickname;
    private String phone_number;
    private List<UserProfileImageDto> profile_image_url;

}
