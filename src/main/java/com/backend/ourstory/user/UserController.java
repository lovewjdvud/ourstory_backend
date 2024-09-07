package com.backend.ourstory.user;


import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.common.dto.ExceptionEnum;
import com.backend.ourstory.common.dto.JwtToken;
import com.backend.ourstory.common.exception.ApiException;
import com.backend.ourstory.common.util.SecurityUtil;
import com.backend.ourstory.user.dto.Request.SignInDto;
import com.backend.ourstory.user.dto.Request.UserDto;
import com.backend.ourstory.user.dto.Request.UserProfileUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User", description = "회원")
public class UserController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/join") // TODO: headers -> customer_id
    @Operation(summary = "회원가입", description = "회원가입 요청 API")
    public ResponseEntity<ApiResult> join(@Valid @RequestBody UserDto userDto)   {
        LOGGER.info("회원가입 요청 : email = {}, name = {}", userDto.getEmail(), userDto.getName());
//        if ("AuthenticationException".equals(id)) {
//            throw new ApiException(ExceptionEnum.SECURITY);
//        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.userJoin(userDto));
    }

    @PostMapping("/sign-in") // TODO: headers -> customer_id
    @Operation(summary = "로그인", description = "로그인 요청 API")
    public ResponseEntity<ApiResult> login(@RequestBody SignInDto signInDto)   {
        LOGGER.info("로그인 요청 : Contoller ");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.signIn(signInDto));
    }
    @PostMapping("/test")
    public ResponseEntity<ApiResult> test(@RequestBody SignInDto signInDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.test());
    }
    @GetMapping("/info/{user_id}") // TODO: headers -> customer_id
    @Operation(summary = "유저 프로필 정보", description = "유저 프로필 정보 API")
    public ResponseEntity<ApiResult> userProfileInfo(@PathVariable int user_id)   {
        LOGGER.info("유저 프로필 정보 요청 : Contoller ");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.userProfileInfo(user_id));
    }

    @PatchMapping("/info/update/{user_id}") // TODO: headers -> customer_id
    @Operation(summary = "유저 프로필 정보 업데이트", description = "유저 프로필 정보 업데이트 API")
    public ResponseEntity<ApiResult> userProfileInfoUpdate(@PathVariable int user_id, @RequestBody UserProfileUpdateDto userProfileUpdateDto)   {
        LOGGER.info("유저 프로필 정보 업데이트 요청 : Contoller");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.userProfileInfoUpdate(user_id,userProfileUpdateDto));
    }

    @PostMapping(value="/profile-image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저 프로필 이미지 업데이트", description = "유저 프로필 이미지 업데이트 API")
    public ResponseEntity<ApiResult> uploadFile(@RequestParam("file") @NonNull MultipartFile file) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.userProfileImageFileUpload(file));
    }


    @DeleteMapping(value="/profile-image/delete")
    @Operation(summary = "유저 프로필 이미지 삭제", description = "유저 프로필 이미지 삭제")
    public ResponseEntity<ApiResult> profileImageDelete(@RequestParam @NonNull long fileId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.profileImageDelete(fileId));
    }
//    @Operation(summary = "파일 등록", description = "멀티파트 타입 데이터로 전송")
//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<UploadResponseDTO> upload(@Valid @Parameter(
//            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE) ) UploadFileDTO uploadFileDTO)

// 2번째

//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<UploadSavedDTO> upload(@Valid @FilesParameter UploadFileDTO uploadFileDTO)


}
