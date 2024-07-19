package com.backend.ourstory.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "Comment", description = "댓글")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/") // TODO: headers -> customer_id
    @Operation(summary = "업체 회원가입", description = "업체 측에서 회원가입 할 때 사용하는 API")

    public String getAccount() {
        return  "sss";
        //return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(customerId));
    }
//
//    @GetMapping("/{accountNumber}/detail")
//    public ResponseEntity<SpecificAccount> getAccountDetail(@PathVariable String accountNumber,
//                                                            @RequestParam long customerId,
//                                                            @RequestParam LocalDateTime viewYearMonth) {
//        // TODO: customerId 검증
//        return  ResponseEntity.ok(commentService.getAccountDetail(accountNumber,viewYearMonth));
//    }
}
