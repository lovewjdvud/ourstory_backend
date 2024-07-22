package com.backend.ourstory.board;


import com.backend.ourstory.board.dto.request.BoardAddDto;
import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping("/add") // TODO: headers -> customer_id
    @Operation(summary = "게시글 생성", description = "게시글 생성하는 API")
    public  ResponseEntity<ApiResult> addBoard(@RequestBody BoardAddDto boardAddDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.boardAdd(boardAddDto));
    }

}
