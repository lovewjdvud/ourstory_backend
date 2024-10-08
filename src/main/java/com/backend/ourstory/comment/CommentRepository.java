package com.backend.ourstory.comment;

import com.backend.ourstory.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Optional<Comment>> findCommentByBoardId(long boardId);

    // 특정 boardId에 해당하는 댓글 총 개수 가져오기
    int countByBoardId(long boardId);

    @Modifying
    @Query("UPDATE Comment c SET c.content = :#{#comment.content} WHERE c.id = :id")
    int updateCommentById(Comment comment,long id);
}
