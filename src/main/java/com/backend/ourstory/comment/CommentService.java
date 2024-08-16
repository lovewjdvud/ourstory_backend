package com.backend.ourstory.comment;


import com.backend.ourstory.board.BoardEntity;
import com.backend.ourstory.board.BoardRepository;
import com.backend.ourstory.board.BoardService;
import com.backend.ourstory.board.dto.request.BoardAddDto;
import com.backend.ourstory.comment.dto.request.CommentAddDto;
import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.common.dto.ExceptionEnum;
import com.backend.ourstory.common.dto.ResponseStatus;
import com.backend.ourstory.common.exception.ApiException;
import com.backend.ourstory.common.util.FileUtils;
import com.backend.ourstory.common.util.SecurityUtil;
import com.backend.ourstory.user.UserEntity;
import com.backend.ourstory.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    @Transactional
    // 댓글 생성
    public ApiResult addComment(CommentAddDto commentAddDto) {
        UserEntity userEntity = userRepository.findByEmail(SecurityUtil.getCurrentUsername());

        logger.info("댓글 생성 처리 유저 아이디: {} , 유저 닉네임: {}, 유저 url  총카운트 : {}", userEntity.getId(),userEntity.getNickname(),userEntity.getProfileImageUrl());

        Comment.CommentBuilder builder = Comment.builder();
        builder.content(commentAddDto.getContent());
        builder.boardId(commentAddDto.getBoardId());
        builder.user_id(userEntity.getId());
        builder.user_nick_name(userEntity.getNickname());
        builder.user_image_url(userEntity.getProfileImageUrl());

        Comment comment = builder
                .build();


        try {
            Comment savedBoarder  = commentRepository.save(comment);

            int commentCount = commentRepository.countByBoardId(commentAddDto.getBoardId());

            // 해당 게시들 댓글 카운트 업데이트
            int boardEntity = boardRepository.updateCommentCountById(commentAddDto.getBoardId(),commentCount);

            BoardEntity boardEntyti = boardRepository.findById(commentAddDto.getBoardId())
                    .orElseThrow(() ->
                            new ApiException(ExceptionEnum.SEARCH_DATA_NULL_ERROR)
                    );

            logger.info(" 댓글 생성 처리 완료: {}  총카운트 : {} ,board 카운트 : {} ", boardEntity,boardEntyti.getComment_count());

            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .detail_msg("댓글 생성 완료되었습니다.")
                    .build();

        } catch (DataAccessException e) {
            logger.error("댓글 생성 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("댓글 생성 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }

    // 댓글 삭제
    public ApiResult deleteComment(long board_id) {

        List<Optional<Comment>> userEntity = commentRepository.findCommentByBoardId(board_id);

        return ApiResult.builder()
                .status(ResponseStatus.SUCCESS)
                .data(userEntity)
                .build();
    }

    // 댓글 수정
    public ApiResult updateComment(long board_id) {

        List<Optional<Comment>> userEntity = commentRepository.findCommentByBoardId(board_id);

        return ApiResult.builder()
                .status(ResponseStatus.SUCCESS)
                .data(userEntity)
                .build();
    }

    // 댓글 리스트
    public ApiResult getCommentList(long board_id) {

        List<Optional<Comment>> userEntity = commentRepository.findCommentByBoardId(board_id);

        return ApiResult.builder()
                .status(ResponseStatus.SUCCESS)
                .data(userEntity)
                .build();
    }
}
