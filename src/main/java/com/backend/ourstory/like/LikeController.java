package com.backend.ourstory.like;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService likeService;
}
