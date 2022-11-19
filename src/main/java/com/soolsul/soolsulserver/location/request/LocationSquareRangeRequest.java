package com.soolsul.soolsulserver.location.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public record LocationSquareRangeRequest(
        @Min(-90) @Max(90) double latitude, // 위도
        @Min(-180) @Max(180) double longitude, // 경도
        @Min(1) @Max(9) int level // 확대 레벨
) {
}
