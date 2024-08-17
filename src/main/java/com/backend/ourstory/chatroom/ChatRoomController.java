package com.backend.ourstory.chatroom;

import com.backend.ourstory.board.BoardService;
import com.backend.ourstory.board.dto.request.BoardAddDto;
import com.backend.ourstory.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
@Tag(name = "ChatRoom", description = "게시물")
public class ChatRoomController {
    private final BoardService boardService;
//    @GetMapping("/{user_id}") // TODO: headers -> customer_id
//    @Operation(summary = "채팅방 리스트", description = "개인 채팅방 리스트 API")
//    public ResponseEntity<ApiResult> addBoard(@PathVariable int user_id) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(boardService.addBoard(boardAddDto));
//    }

}
