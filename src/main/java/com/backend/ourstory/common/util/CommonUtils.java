package com.backend.ourstory.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CommonUtils {

    public static long getCurrentTimestamp() {
        // 현재 시간을 LocalDateTime 객체로 가져옴
        LocalDateTime now = LocalDateTime.now();

        // LocalDateTime을 Instant로 변환 (UTC 기준)
        Instant instant = now.toInstant(ZoneOffset.UTC);

        // Instant를 에포크 밀리초 (타임스탬프)로 변환
        return instant.toEpochMilli();
    }

}
