package com.soolsul.soolsulserver.bar.businees.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record BarLookupServiceConditionRequest(
        @NotEmpty double southWestLatitude, //남서위도 (minY)
        @NotEmpty double southWestLongitude, //남서경도 (minX)
        @NotEmpty double northEastLatitude, //북동위도 (maxY)
        @NotEmpty double northEastLongitude, //북동경도 (maxX)
        @NotNull List<String> barMoodTagIds,
        @NotNull List<String> barAlcoholTagIds
) {
}
