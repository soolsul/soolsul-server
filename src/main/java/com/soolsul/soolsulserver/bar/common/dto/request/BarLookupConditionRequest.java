package com.soolsul.soolsulserver.bar.common.dto.request;

import com.soolsul.soolsulserver.location.annotation.Latitude;
import com.soolsul.soolsulserver.location.annotation.Longitude;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record BarLookupConditionRequest(
        @Latitude double latitude,
        @Longitude double longitude,
        @NotEmpty int level,
        @NotNull String barMoodTagNames,
        @NotNull String barAlcoholTagNames
) {
}
