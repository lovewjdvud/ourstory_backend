package com.backend.ourstory.user;

import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.common.dto.ResponseStatus;
import com.backend.ourstory.user.dto.Request.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    // 회원가입
    public ApiResult userJoin(UserDto userDto){ // vym = 2023.05
//        BCryptPasswordEncoder bCryptPasswordEncoder;

        Boolean isExist = userRepository.existsByEmail(userDto.getEmail());

        if (isExist) {
            return ApiResult.builder()
                    .status(ResponseStatus.FAILURE)
                    .detail_msg("아이디가 존재합니다")
                    .build();
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .nickname(userDto.getNickname())
                .phone_number(userDto.getPhoneNumber())
                .profile_image_url(userDto.getProfileImageUrl())
                .build();

        try {
            User savedUser = userRepository.save(user);
            logger.info("회원가입 처리 완료: {}", savedUser.getEmail());

            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .detail_msg("회원가입이 완료되었습니다.")
                    .data(savedUser.getEmail())
                    .build();

        } catch (DataAccessException e) {
            logger.error("회원가입 처리 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("회원가입 처리 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }

    }
}
