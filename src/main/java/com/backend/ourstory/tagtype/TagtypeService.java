package com.backend.ourstory.tagtype;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagtypeService {
    private final TagtypeRepository tagtypeRepository;
}
