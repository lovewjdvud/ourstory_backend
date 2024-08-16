package com.backend.ourstory.tagtype;

import com.backend.ourstory.board.BoardEntity;
import com.backend.ourstory.board.BoardService;
import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.common.dto.ExceptionEnum;
import com.backend.ourstory.common.dto.ResponseStatus;
import com.backend.ourstory.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagtypeService {
    private final TagtypeRepository tagtypeRepository;

    private static final Logger logger = LoggerFactory.getLogger(TagtypeService.class);
    // 태그 타입 가져오기
    public ApiResult getTagList() {
        try {
            List<Tagtype> tagtypeList = tagtypeRepository.findAllBy();
            logger.info("게시글 생성 처리 완료: {}", tagtypeList.stream().count());

            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(tagtypeList)
                    .build();

        } catch (DataAccessException e) {

            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("Tag Type을 불러오는 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }
}
