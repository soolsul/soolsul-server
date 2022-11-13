package com.soolsul.soolsulserver.post.presentation.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record UserLocationRequest(

        @NotNull
        @Min(-180)
        @Max(180)
        double longitude,

        @NotNull
        @Min(-90)
        @Max(90)
        double latitude,

        @NotNull
        @Min(0)
        int radius
) {
}
