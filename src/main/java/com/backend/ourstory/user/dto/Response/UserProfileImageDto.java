package com.backend.ourstory.user.dto.Response;

import com.backend.ourstory.common.entity.BaseEntity;
import com.backend.ourstory.common.exception.BaseException;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserProfileImageDto {
    private long id;
    private String urlPath;
    private LocalDateTime createdAt;

}
