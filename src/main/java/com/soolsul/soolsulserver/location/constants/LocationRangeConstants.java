package com.soolsul.soolsulserver.location.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocationRangeConstants {

    public static final double MIN_LATITUDE_RANGE = -90.0;
    public static final double MAX_LATITUDE_RANGE = 90.0;

    public static final double MIN_LONGITUDE_RANGE = -180.0;
    public static final double MAX_LONGITUDE_RANGE = 180.0;


    public static final int MAX_RADIUS_RANGE = 20000;
    public static final int MIN_RADIUS_RANGE = 100;

}
