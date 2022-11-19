package com.soolsul.soolsulserver.location.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public record LocationSquareRangeRequest(
        @Min(-90) @Max(90) double latitude,
        @Min(-180) @Max(180) double longitude,
        @Min(1) @Max(9) int level
) {
}
