package com.backend.ourstory.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository  extends JpaRepository<BoardEntity, Long>  {
    List<BoardEntity> findByTagtype(String tagtype);

    Optional<BoardEntity> findById(int boardId);

    @Modifying
    @Query("UPDATE BoardEntity b SET b.comment_count = :commentCount WHERE b.id = :boardId")
    int updateCommentCountById(@Param("boardId") long boardId, @Param("commentCount") int commentCount);

}
