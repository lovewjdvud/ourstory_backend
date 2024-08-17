package com.backend.ourstory.board;


import com.backend.ourstory.board.dto.request.BoardAddDto;
import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
@Tag(name = "Board", description = "게시물")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/") // TODO: headers -> customer_id
    @Operation(summary = "게시글 리스트", description = "게시글 리스트API")
    public  ResponseEntity<ApiResult> getBoardList(@RequestParam(defaultValue = "A") String tag_type) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.getboardList(tag_type));
    }

    @PostMapping("/add") // TODO: headers -> customer_id
    @Operation(summary = "게시글 생성", description = "게시글 생성하는 API")
    public  ResponseEntity<ApiResult> addBoard(@RequestBody BoardAddDto boardAddDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.addBoard(boardAddDto));
    }

    @GetMapping("/detail") // TODO: headers -> customer_id
    @Operation(summary = "게시글 상세보기", description = "게시글 상세보기 API")
    public  ResponseEntity<ApiResult> detailBoard(@RequestParam @NonNull int boardId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.detailBoard(boardId));
    }


    @DeleteMapping("/delete") // TODO: headers -> customer_id
    @Operation(summary = "게시글 삭제하기", description = "게시글 삭제하기 API")
    public  ResponseEntity<ApiResult> deleteBoard(@RequestParam @NonNull int boardId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.deleteBoard(boardId));
    }

    @PostMapping("/like") // TODO: headers -> customer_id
    @Operation(summary = "게시글 좋아요 생성", description = "게시글 좋아요 생성 API")
    public  ResponseEntity<ApiResult> likeBoard(@RequestParam @NonNull int boardId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.deleteBoard(boardId));
    }

    @DeleteMapping("/like/delete") // TODO: headers -> customer_id
    @Operation(summary = "게시글 좋아요 삭제하기", description = "게시글 좋아요 삭제하기 API")
    public  ResponseEntity<ApiResult> likeDeleteBoard(@RequestParam @NonNull int boardId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.deleteBoard(boardId));
    }

    // DetailBoard

}
