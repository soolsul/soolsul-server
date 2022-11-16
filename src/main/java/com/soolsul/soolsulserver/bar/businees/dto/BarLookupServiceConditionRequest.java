package com.soolsul.soolsulserver.bar.businees.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public record BarLookupServiceConditionRequest(
        @NotEmpty double southWestLatitude,
        @NotEmpty double southWestLongitude,
        @NotEmpty double northEastLatitude,
        @NotEmpty double northEastLongitude,
        @NotEmpty List<String> barMoodTagIds,
        @NotEmpty List<String> barAlcoholTagIds
) {
}
