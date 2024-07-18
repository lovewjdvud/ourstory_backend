package com.backend.ourstory.board;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/board")
@Tag(name = "Response Estimate", description = "Response Estimate API")
@ResponseBody
public class BoardController {

    @GetMapping("/") // TODO: headers -> customer_id
    @Operation(summary = "업체 회원가입", description = "업체 측에서 회원가입 할 때 사용하는 API")
    public String getAccount() {
        return  "sss";
        //return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(customerId));
    }

}
