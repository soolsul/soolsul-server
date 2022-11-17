package com.soolsul.soolsulserver.common.userlocation;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserLocationBasedSquareRange {
    //meter 당 위도,경도를 구할때 사용합니다.
    private static final double EARTH_RADIUS = 6371;
    private static final Map<Integer, Integer> levelMap = new HashMap<>();

    static {
        levelMap.put(1, 20);
        levelMap.put(2, 30);
        levelMap.put(3, 50);
        levelMap.put(4, 100);
        levelMap.put(5, 250);
        levelMap.put(6, 500);
        levelMap.put(7, 1000);
        levelMap.put(8, 2000);
        levelMap.put(9, 4000);
        levelMap.put(10, 8000);
        levelMap.put(11, 16000);
        levelMap.put(12, 32000);
        levelMap.put(13, 64000);
        levelMap.put(14, 128000);
    }

    private final double maxX;
    private final double maxY;
    private final double minX;
    private final double minY;

    //참고 : https://wildeveloperetrain.tistory.com/171
    public UserLocationBasedSquareRange(UserLocation locationInfo) {
        double meterForLatitude = (1 / Math.toRadians(EARTH_RADIUS)) / 1000;
        double meterForLongitude = (1 / (Math.toRadians(EARTH_RADIUS) * Math.cos(Math.toRadians(
                locationInfo.getLongitude())))) / 1000;

        this.maxX = calculateMaxPoint(locationInfo.getLongitude(), locationInfo.getLevel(), meterForLongitude);
        this.maxY = calculateMaxPoint(locationInfo.getLatitude(), locationInfo.getLevel(), meterForLatitude);
        this.minX = calculateMinPoint(locationInfo.getLongitude(), locationInfo.getLevel(), meterForLongitude);
        this.minY = calculateMinPoint(locationInfo.getLatitude(), locationInfo.getLevel(), meterForLatitude);
    }

    private double calculateMaxPoint(double point, int level, double meterForPoint) {
        int radius = levelMap.get(level);
        return point + (radius * meterForPoint);
    }

    private double calculateMinPoint(double point, int level, double meterForPoint) {
        int radius = levelMap.get(level);
        return point - (radius * meterForPoint);
    }
}
