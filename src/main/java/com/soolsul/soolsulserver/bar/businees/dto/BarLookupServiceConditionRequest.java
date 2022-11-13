package com.soolsul.soolsulserver.bar.businees.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public record BarLookupServiceConditionRequest(
        @NotEmpty double southWestLongitude,
        @NotEmpty double southWestLatitude,
        @NotEmpty double northEastLongitude,
        @NotEmpty double northEastLatitude,
        @NotEmpty List<String> barMoodTagIds,
        @NotEmpty List<String> barAlcoholTagIds
) {
}
