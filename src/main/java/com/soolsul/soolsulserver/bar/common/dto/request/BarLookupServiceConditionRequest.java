package com.soolsul.soolsulserver.bar.common.dto.request;

import com.soolsul.soolsulserver.location.annotation.Latitude;
import com.soolsul.soolsulserver.location.annotation.Longitude;

import javax.validation.constraints.NotNull;
import java.util.List;

public record BarLookupServiceConditionRequest(
        @Latitude double southWestLatitude, //남서위도 (minY)
        @Longitude double southWestLongitude, //남서경도 (minX)
        @Latitude double northEastLatitude, //북동위도 (maxY)
        @Longitude double northEastLongitude, //북동경도 (maxX)
        @NotNull List<String> barMoodTagIds,
        @NotNull List<String> barAlcoholTagIds
) {
}
