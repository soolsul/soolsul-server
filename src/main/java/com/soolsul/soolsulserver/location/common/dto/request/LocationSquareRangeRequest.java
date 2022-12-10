package com.soolsul.soolsulserver.location.common.dto.request;

import com.soolsul.soolsulserver.location.annotation.Latitude;
import com.soolsul.soolsulserver.location.annotation.Longitude;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public record LocationSquareRangeRequest(
        @Latitude double latitude, // 위도
        @Longitude double longitude, // 경도
        @Min(1) @Max(9) int level // 확대 레벨
) {
}
