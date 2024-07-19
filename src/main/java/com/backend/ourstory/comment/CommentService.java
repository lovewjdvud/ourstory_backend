package com.backend.ourstory.comment;


import com.backend.ourstory.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
}
