package com.backend.ourstory.tagtype;

import com.backend.ourstory.comment.CommentService;
import com.backend.ourstory.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tagtype")
@Tag(name = "Tagtype", description = "태그 타입")
public class TagtypeController {
    private final TagtypeService tagtypeService;

    @GetMapping("/") // TODO: headers -> customer_id
    @Operation(summary = "태그 타입 리스트", description = "태그 타입 API")
    public ResponseEntity<ApiResult> detailBoard() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagtypeService.getTagList());
    }

}
