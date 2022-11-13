package com.soolsul.soolsulserver.common.userlocation;

import lombok.Getter;

@Getter
public class UserLocationBasedSquareRange {
    //meter 당 위도,경도를 구할때 사용합니다.
    private static final double EARTH_RADIUS = 6371;

    private final double maxX;
    private final double maxY;
    private final double minX;
    private final double minY;

    //참고 : https://wildeveloperetrain.tistory.com/171
    public UserLocationBasedSquareRange(UserLocation locationInfo) {
        double meterForLatitude = (1 / Math.toRadians(EARTH_RADIUS)) / 1000;
        double meterForLongitude = (1 / (Math.toRadians(EARTH_RADIUS) * Math.cos(Math.toRadians(
                locationInfo.getLongitude())))) / 1000;

        this.maxX = calculateMaxPoint(locationInfo.getLatitude(), locationInfo.getRadius(), meterForLongitude);
        this.maxY = calculateMaxPoint(locationInfo.getLongitude(), locationInfo.getRadius(), meterForLatitude);
        this.minX = calculateMinPoint(locationInfo.getLatitude(), locationInfo.getRadius(), meterForLongitude);
        this.minY = calculateMinPoint(locationInfo.getLongitude(), locationInfo.getRadius(), meterForLatitude);
    }

    private double calculateMaxPoint(double point, int radius, double meterForPoint) {
        return point + (radius * meterForPoint);
    }

    private double calculateMinPoint(double point, int radius, double meterForPoint) {
        return point - (radius * meterForPoint);
    }
}
