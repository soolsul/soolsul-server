package com.soolsul.soolsulserver.bar.presentation.dto;

import javax.validation.constraints.NotEmpty;

public record BarLookupConditionRequest(
        @NotEmpty double southWestLongitude,
        @NotEmpty double southWestLatitude,
        @NotEmpty double northEastLongitude,
        @NotEmpty double northEastLatitude,
        @NotEmpty String barMoodTagNames,
        @NotEmpty String barAlcoholTagNames
) {
}
