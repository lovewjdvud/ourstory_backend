package com.backend.ourstory.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository  extends JpaRepository<BoardEntity, Long>  {
    List<BoardEntity> findByTagtype(String tagtype);
}
