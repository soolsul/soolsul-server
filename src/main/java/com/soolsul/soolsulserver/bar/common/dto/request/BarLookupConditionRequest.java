package com.soolsul.soolsulserver.bar.common.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record BarLookupConditionRequest(
        @NotEmpty double latitude,
        @NotEmpty double longitude,
        @NotEmpty int level,
        @NotNull String barMoodTagNames,
        @NotNull String barAlcoholTagNames
) {
}
