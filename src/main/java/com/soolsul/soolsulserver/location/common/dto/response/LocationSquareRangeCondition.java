package com.soolsul.soolsulserver.location.common.dto.response;

import com.soolsul.soolsulserver.location.annotation.Latitude;
import com.soolsul.soolsulserver.location.annotation.Longitude;

public record LocationSquareRangeCondition(
        @Latitude double southWestLatitude, // 남서 위도 (minX)
        @Longitude double southWestLongitude, // 남서 경도 (minY)
        @Latitude double northEastLatitude, // 북동 위도 (maxX)
        @Longitude double northEastLongitude //북동 경도 (maxY)
) {
}
