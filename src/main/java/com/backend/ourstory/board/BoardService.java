package com.backend.ourstory.board;

import com.backend.ourstory.board.dto.request.BoardAddDto;
import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.common.dto.ResponseStatus;
import com.backend.ourstory.common.util.SecurityUtil;
import com.backend.ourstory.user.UserEntity;
import com.backend.ourstory.user.UserService;
import com.backend.ourstory.user.dto.Request.SignInDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    public ApiResult getboardList(String tagType) {
        try {
            List<BoardEntity> boardList = boardRepository.findByTagtype(tagType);
            logger.info("게시글 생성 처리 완료: {}", boardList.stream().count());

            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(boardList)
                    .build();

        } catch (DataAccessException e) {
            logger.error("게시글 리스트를 불러오는 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("게시글 리스트를 불러오는 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }

    // 게시물 생성
    public ApiResult boardAdd(BoardAddDto boardAddDto) {

        BoardEntity.BoardEntityBuilder builder = BoardEntity.builder();
        builder.title(boardAddDto.getTitle());
        builder.content(boardAddDto.getContent());
        builder.tagtype(boardAddDto.getTag_type());
        builder.user_id(boardAddDto.getUser_id());
        builder.user_nickname(boardAddDto.getUser_nickname());
        builder.user_image_url(boardAddDto.getUser_profile_image());

        BoardEntity board = builder
                .build();

        try {
            BoardEntity savedBoarder = boardRepository.save(board);
            logger.info("게시글 생성 처리 완료: {}", savedBoarder.getTitle());

            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .detail_msg("게시글 생성 완료되었습니다.")
                    .data(savedBoarder)
                    .build();

        } catch (DataAccessException e) {
            logger.error("게시글 생성 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("게시글 생성 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }



}
