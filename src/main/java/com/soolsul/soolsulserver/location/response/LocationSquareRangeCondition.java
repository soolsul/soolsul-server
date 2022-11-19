package com.soolsul.soolsulserver.location.response;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public record LocationSquareRangeCondition(
        @Min(-90) @Max(90) double southWestLatitude, // 남서 위도 (minX)
        @Min(-180) @Max(180) double southWestLongitude, // 남서 경도 (minY)
        @Min(-90) @Max(90) double northEastLatitude, // 북동 위도 (maxX)
        @Min(-180) @Max(180) double northEastLongitude //북동 경도 (maxY)
) {
}
