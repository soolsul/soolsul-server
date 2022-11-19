package com.soolsul.soolsulserver.location.response;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public record LocationSquareRangeCondition(
        @Min(-90) @Max(90) double minLatitude,
        @Min(-90) @Max(90) double maxLatitude,
        @Min(-180) @Max(180) double minLongitude,
        @Min(-180) @Max(180) double maxLongitude
) {
}
