package com.backend.ourstory.tagtype;

import com.backend.ourstory.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tagtype")
public class TagtypeController {
    private final TagtypeService tagtypeService;

}
