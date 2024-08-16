package com.backend.ourstory.comment;

import com.backend.ourstory.board.dto.request.BoardAddDto;
import com.backend.ourstory.comment.dto.request.CommentAddDto;
import com.backend.ourstory.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "Comment", description = "댓글")
public class CommentController {
    private final CommentService commentService;


    @PostMapping(value="/add")
    @Operation(summary = "댓글 생성", description = "댓글 생성 API")
    public ResponseEntity<ApiResult>
    getCommentList(@RequestBody CommentAddDto commentAddDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.addComment(commentAddDto));
    }

    @DeleteMapping(value="/delete")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제 API")
    public ResponseEntity<ApiResult> deleteComment(@RequestParam("board_id") @NonNull int board_id,@RequestParam("comment_id") @NonNull int comment_id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.deleteComment(board_id,comment_id));
    }

    @PatchMapping(value="/update/{comment_id}")
    @Operation(summary = "댓글 수정", description = "댓글 수정 API")
    public ResponseEntity<ApiResult> updateComment(@RequestBody CommentAddDto commentAddDto,@PathVariable int comment_id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(commentAddDto,comment_id));
    }

    @GetMapping(value="/list")
    @Operation(summary = "댓글 리스트", description = "댓글 리스트 API")
    public ResponseEntity<ApiResult> getCommentList(@RequestParam("board_id") @NonNull long board_id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentList(board_id));
    }


}
