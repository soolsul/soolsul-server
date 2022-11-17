package com.soolsul.soolsulserver.bar.businees.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public record BarLookupServiceConditionRequest(
        @NotEmpty double northEastLongitude, // maxX
        @NotEmpty double northEastLatitude, // maxY
        @NotEmpty double southWestLongitude, // minX
        @NotEmpty double southWestLatitude, // minY
        @NotEmpty List<String> barMoodTagIds,
        @NotEmpty List<String> barAlcoholTagIds
) {
}
