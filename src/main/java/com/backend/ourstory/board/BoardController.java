package com.backend.ourstory.board;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
@Tag(name = "Board", description = "게시물")
@ResponseBody
public class BoardController {

    @GetMapping("/") // TODO: headers -> customer_id
    @Operation(summary = "업체 회원가입", description = "업체 측에서 회원가입 할 때 사용하는 API")
    @Parameters({
            @Parameter(name = "email", description = "이메일", example = "chrome123@naver.com"),
            @Parameter(name = "password", description = "6자~12자 이내", example = "abcd1234"),
            @Parameter(name = "companyName", description = "업체명", example = "코리아 시스템"),
            @Parameter(name = "companyNumber", description = "업체 번호", example = "112233"),
            @Parameter(name = "companyAddress", description = "업체 주소", example = "인천시 미추홀구 용현동")
    })
    public String getAccount(@PathVariable String id) {
        return  "sss";
        //return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(customerId));
    }

}
