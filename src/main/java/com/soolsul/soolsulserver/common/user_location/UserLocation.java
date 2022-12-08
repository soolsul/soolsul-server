package com.soolsul.soolsulserver.common.user_location;

import lombok.Getter;

@Getter
public class UserLocation {

    private static final double MAX_LATITUDE_RANGE = 90.0;
    private static final double MIN_LATITUDE_RANGE = -90.0;
    private static final double MAX_LONGITUDE_RANGE = 180.0;
    private static final double MIN_LONGITUDE_RANGE = -180.0;
    private static final int MAX_RADIUS_RANGE = 14;
    private static final int MIN_RADIUS_RANGE = 1;

    private double latitude; // x
    private double longitude; // y
    private int level;

    private UserLocation(double latitude, double longitude, int level) {
        if (!(validateLatitude(latitude) && validateLongitude(longitude) && validateLevel(level))) {
            throw new IllegalArgumentException();
        }
        this.latitude = latitude;
        this.longitude = longitude;
        this.level = level;
    }

    private static boolean validateLevel(int radius) {
        return MIN_RADIUS_RANGE <= radius && radius <= MAX_RADIUS_RANGE;
    }

    private static boolean validateLongitude(double longitude) {
        return MIN_LONGITUDE_RANGE <= longitude && longitude <= MAX_LONGITUDE_RANGE;
    }

    private static boolean validateLatitude(double latitude) {
        return MIN_LATITUDE_RANGE <= latitude && latitude <= MAX_LATITUDE_RANGE;
    }

    public static UserLocation of(double latitude, double longitude, int level) {
        return new UserLocation(latitude, longitude, level);
    }
}
