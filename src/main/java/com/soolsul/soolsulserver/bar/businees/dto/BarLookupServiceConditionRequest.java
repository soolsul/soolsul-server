package com.soolsul.soolsulserver.bar.businees.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record BarLookupServiceConditionRequest(
        @NotEmpty double northEastLongitude, // maxX
        @NotEmpty double northEastLatitude, // maxY
        @NotEmpty double southWestLongitude, // minX
        @NotEmpty double southWestLatitude, // minY
        @NotNull List<String> barMoodTagIds,
        @NotNull List<String> barAlcoholTagIds
) {
}
