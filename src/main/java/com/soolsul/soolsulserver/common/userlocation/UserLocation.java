package com.soolsul.soolsulserver.common.userlocation;

import com.soolsul.soolsulserver.post.presentation.dto.UserLocationRequest;
import lombok.Getter;

@Getter
public class UserLocation {

    private static final double MAX_LATITUDE_RANGE = 90.0;
    private static final double MIN_LATITUDE_RANGE = -90.0;
    private static final double MAX_LONGITUDE_RANGE = 180.0;
    private static final double MIN_LONGITUDE_RANGE = -180.0;
    private static final int MAX_RADIUS_RANGE = 20000;
    private static final int MIN_RADIUS_RANGE = 100;

    private double latitude; // x
    private double longitude; // y
    private int radius;

    private UserLocation(double latitude, double longitude, int radius) {
        if (!(validateLongitude(latitude) && validateLatitude(longitude) && validateRadius(radius))) {
            throw new IllegalArgumentException();
        }
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    private static boolean validateRadius(int radius) {
        return MIN_RADIUS_RANGE <= radius && radius <= MAX_RADIUS_RANGE;
    }

    private static boolean validateLongitude(double longitude) {
        return MIN_LONGITUDE_RANGE <= longitude && longitude <= MAX_LONGITUDE_RANGE;
    }

    private static boolean validateLatitude(double latitude) {
        return MIN_LATITUDE_RANGE <= latitude && latitude <= MAX_LATITUDE_RANGE;
    }

    public static UserLocation from(UserLocationRequest locationRequest) {
        return new UserLocation(locationRequest.latitude(), locationRequest.longitude(), locationRequest.radius());
    }
}
