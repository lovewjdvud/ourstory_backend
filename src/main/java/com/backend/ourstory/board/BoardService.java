package com.backend.ourstory.board;

import com.backend.ourstory.board.dto.request.BoardAddDto;
import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.common.dto.ExceptionEnum;
import com.backend.ourstory.common.dto.ResponseStatus;
import com.backend.ourstory.common.exception.ApiException;
import com.backend.ourstory.common.util.SecurityUtil;
import com.backend.ourstory.like.Like;
import com.backend.ourstory.like.LikeRepository;
import com.backend.ourstory.user.UserEntity;
import com.backend.ourstory.user.UserRepository;
import com.backend.ourstory.user.UserService;
import com.backend.ourstory.user.dto.Request.SignInDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    // 게시글 리스트
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
                    .detail_msg("게시글 리스트를 불러오는 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }

    // 게시물 생성
    public ApiResult addBoard(BoardAddDto boardAddDto) {

        BoardEntity.BoardEntityBuilder builder = BoardEntity.builder();
        builder.title(boardAddDto.getTitle());
        builder.content(boardAddDto.getContent());
        builder.tagtype(boardAddDto.getTag_type());
        builder.user_id(boardAddDto.getUser_id());
        builder.user_nick_name(boardAddDto.getUser_nickname());
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


    // 게시글 상세보기
    public ApiResult detailBoard(int boardId) {

        try {

            BoardEntity boardList = boardRepository.findById(boardId)
                 .orElseThrow(() ->
                        new ApiException(ExceptionEnum.SEARCH_DATA_NULL_ERROR)
                );


            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(boardList)
                    .build();

        } catch (DataAccessException e) {
            logger.error("게시글 상세보기 불러오는 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("게시글 상세보기 불러오는 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }

    // 게시글 삭제하기
    public ApiResult deleteBoard(int boardId) {

        try {
            String userEmail = SecurityUtil.getCurrentUsername();
            Optional<UserEntity> userEntity = userRepository.findUserByEmail(userEmail);

            BoardEntity board = boardRepository.findById(boardId)
                    .orElseThrow(() ->
                            new ApiException(ExceptionEnum.SEARCH_DATA_NULL_ERROR)
                    );

            boardRepository.delete(board);

            if (board.getUser_id() != (int) userEntity.get().getId()) {
                return ApiResult.builder()
                        .status(ResponseStatus.FAILURE)
                        .detail_msg("해당 게시물은 작성자만 삭제 가능합니다 board user id " + board.getUser_id() + "user" + userEntity.get().getId())
                        .build();
            }

            boardRepository.deleteById((long) boardId);

            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .detail_msg("삭제가 완료 되었습니다.")
                    .build();

        } catch (DataAccessException e) {
            logger.error("게시글 상세보기 불러오는 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("게시글 상세보기 불러오는 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }


    // 게시글 좋아요 생성
    @Transactional
    public ApiResult likeBoard(long boardId) {

        try {
            UserEntity userEntity = userRepository.findByEmail(SecurityUtil.getCurrentUsername());


            Like.LikeBuilder builder = Like.builder();
            builder.b_id(boardId);
            builder.user_id(userEntity.getId());
            builder.user_nickName(userEntity.getNickname());

            Like like = builder
                    .build();

            // 좋아요 클릭 업데이트
            likeRepository.save(like);

            // 해당 게시물 총 좋아요 카운트 가져오기
            int likeCount = likeRepository.countByB_id(boardId);

            // 해당 게시물 좋아요 카운트 업데이트
            int boardEntity = boardRepository.updateCommentCountById(boardId,likeCount);

            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .build();

        } catch (DataAccessException e) {
            logger.error("게시글 좋아요 생성 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("게시글 좋아요 생성 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }

    // 게시글 좋아요 삭제하기
    public ApiResult likeDeleteBoard(int boardId) {

        try {
            UserEntity userEntity = userRepository.findByEmail(SecurityUtil.getCurrentUsername());

            likeRepository.deleteBy

            BoardEntity boardList = boardRepository.findById(boardId)
                    .orElseThrow(() ->
                            new ApiException(ExceptionEnum.SEARCH_DATA_NULL_ERROR)
                    );


            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(boardList)
                    .build();

        } catch (DataAccessException e) {
            logger.error("게시글 상세보기 불러오는 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("게시글 상세보기 불러오는 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }

}
