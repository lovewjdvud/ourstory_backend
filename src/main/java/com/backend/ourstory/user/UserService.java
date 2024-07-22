package com.backend.ourstory.user;

import com.backend.ourstory.common.config.JwtTokenProvider;
import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.common.dto.JwtToken;
import com.backend.ourstory.common.dto.ResponseStatus;
import com.backend.ourstory.user.dto.CustomUserDetails;
import com.backend.ourstory.user.dto.Request.SignInDto;
import com.backend.ourstory.user.dto.Request.UserDto;
import com.backend.ourstory.user.dto.Request.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    public ApiResult signIn(SignInDto signInDto) {


        String username = signInDto.getUsername();
        String password = signInDto.getPassword();

        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return ApiResult.builder()
                .status(ResponseStatus.SUCCESS)
                .detail_msg("로근인이 완료되었습니다.")
                .data(jwtToken)
                .build();
    }

    @Transactional(readOnly = false)
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

        List<String> roles = new ArrayList<>();
        roles.add("USER");

        UserEntity.UserEntityBuilder builder = UserEntity.builder();
        builder.email(userDto.getEmail());
        builder.password(passwordEncoder.encode(userDto.getPassword()));
        builder.name(userDto.getName());
        builder.nickname(userDto.getNickname());
        builder.phone_number(userDto.getPhoneNumber());
        builder.profile_image_url(userDto.getProfileImageUrl());
        builder.roles(roles);
        UserEntity user = builder
                .build();

        try {
            UserEntity savedUser = userRepository.save(user);
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


//    public ApiResult userLogin(UserLoginDto userLoginDto) { // vym = 2023.05
//
//
//        Boolean isExist = userRepository.existsByEmail(userLoginDto.getEmail());
//
//        if (!isExist) {
//            return ApiResult.builder()
//                    .status(ResponseStatus.FAILURE)
//                    .detail_msg("아이디가 존재하지 않습니다.")
//                    .build();
//        }
//
//        //DB에서 조회
//        UserEntity userData = userRepository.findUserByEmail(userLoginDto.getEmail());
//
//        if (userData != null) {
//
//            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
////            return new CustomUserDetails(userData);
//        }
//
////        return null;
//
//        try {
//            logger.info("로그인 처리 완료: {}");
//            return ApiResult.builder()
//                    .status(ResponseStatus.SUCCESS)
//                    .detail_msg("회원가입이 완료되었습니다.")
//                    .build();
//
//        } catch (DataAccessException e) {
//            logger.error("로그인 처리 중 오류 발생: {}", e.getMessage(), e);
//            return ApiResult.builder()
//                    .status(ResponseStatus.SERVER_ERROR)
//                    .detail_msg("로그인 처리 중 오류가 발생하였습니다. error: \n " + e.getMessage())
//                    .build();
//        }
//    }
}
