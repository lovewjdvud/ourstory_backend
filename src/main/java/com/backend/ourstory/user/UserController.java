package com.backend.ourstory.user;


import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.user.dto.Request.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User", description = "회원")
public class UserController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/join") // TODO: headers -> customer_id
    @Operation(summary = "회원가입", description = "회원가입 요청 API")
    public ResponseEntity<ApiResult> joinProcess(@Valid @RequestBody UserDto userDto)   {
        LOGGER.info("회원가입 요청 : email = {}, name = {}", userDto.getEmail(), userDto.getName());
//        if ("AuthenticationException".equals(id)) {
//            throw new ApiException(ExceptionEnum.SECURITY);
//        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.userJoin(userDto));
    }

}
