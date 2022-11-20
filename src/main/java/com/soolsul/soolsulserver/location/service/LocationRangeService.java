package com.soolsul.soolsulserver.location.service;

import com.soolsul.soolsulserver.location.domain.LocationMagnificationLevel;
import com.soolsul.soolsulserver.location.persistence.LocationMagnificationLevelRepositoryDsl;
import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.response.LocationSquareRangeCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

@Service
@RequiredArgsConstructor
public class LocationRangeService {

    private static final double EARTH_RADIUS = 6371; // meter 당 위도,경도를 구할때 사용하는 상수
    private static final int ONE_LATITUDE = 1; // 위도 1도
    private static final int ONE_LONGITUDE = 1; // 경도 1도
    private static final int METER_PER_KILOMETER = 1000; // 1000m / 1km

    private final LocationMagnificationLevelRepositoryDsl locationMagnificationLevelRepositoryDsl;

    public LocationSquareRangeCondition calculateLocationSquareRange (
            @Valid LocationSquareRangeRequest locationRangeRequest
    ) {
        LocationMagnificationLevel magnificationLevel = locationMagnificationLevelRepositoryDsl.findByMagnificationLevel(
                locationRangeRequest.level());
        int meterAssignedLevel = magnificationLevel.getMeter();

        double meterForLatitude = calculateMeterForLatitude();
        double meterForLongitude = calculateMeterForLongitude(locationRangeRequest.longitude());

        double southWestLatitude = calculateMinPoint(locationRangeRequest.latitude(), meterAssignedLevel, meterForLatitude);
        double southWestLongitude = calculateMinPoint(locationRangeRequest.longitude(), meterAssignedLevel, meterForLongitude);

        double northEastLatitude = calculateMaxPoint(locationRangeRequest.latitude(), meterAssignedLevel, meterForLatitude);
        double northEastLongitude = calculateMaxPoint(locationRangeRequest.longitude(), meterAssignedLevel, meterForLongitude);

        return new LocationSquareRangeCondition(southWestLatitude, northEastLatitude, southWestLongitude, northEastLongitude);
    }

    private double calculateMeterForLatitude() {
        return (ONE_LATITUDE / toRadians(EARTH_RADIUS)) / METER_PER_KILOMETER;
    }

    private double calculateMeterForLongitude(double longitude) {
        return (ONE_LONGITUDE / abs(toRadians(EARTH_RADIUS) * cos(toRadians(longitude)))) / METER_PER_KILOMETER;
    }

    private double calculateMaxPoint(double point, int radiusMeter, double meterForPoint) {
        return point + (radiusMeter * meterForPoint);
    }

    private double calculateMinPoint(double point, int radiusMeter, double meterForPoint) {
        return point - (radiusMeter * meterForPoint);
    }

}
