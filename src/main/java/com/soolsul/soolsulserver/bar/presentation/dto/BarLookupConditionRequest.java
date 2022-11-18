package com.soolsul.soolsulserver.bar.presentation.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record BarLookupConditionRequest(
        @NotEmpty double southWestLongitude,
        @NotEmpty double southWestLatitude,
        @NotEmpty double northEastLongitude,
        @NotEmpty double northEastLatitude,
        @NotNull String barMoodTagNames,
        @NotNull String barAlcoholTagNames
) {
}
