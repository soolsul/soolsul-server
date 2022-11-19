package com.soolsul.soolsulserver.location.service;

import com.soolsul.soolsulserver.location.persistence.LocationMagnificationLevelRepository;
import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.response.LocationSquareRangeCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class LocationRangeService {

    private static final double EARTH_RADIUS = 6371; // meter 당 위도,경도를 구할때 사용하는 상수
    private static final int ONE_LATITUDE = 1; // 위도 1도
    private static final int ONE_LONGITUDE = 1; // 경도 1도
    private static final int METER_PER_KILOMETER = 1000; // 1000m / 1km

    private final LocationMagnificationLevelRepository locationMagnificationLevelRepository;

    public LocationSquareRangeCondition calculateLocationSquareRange (
            @Valid LocationSquareRangeRequest locationRangeRequest
    ) {
        int meterAssignedLevel = locationMagnificationLevelRepository.findByMagnificationLevel(locationRangeRequest.level())
                .getMeter();

        double meterForLatitude = calculateMeterForLatitude();
        double meterForLongitude = calculateMeterForLongitude(locationRangeRequest.longitude());

        double minLatitude = calculateMinPoint(locationRangeRequest.latitude(), meterAssignedLevel, meterForLatitude);
        double maxLatitude = calculateMaxPoint(locationRangeRequest.latitude(), meterAssignedLevel, meterForLatitude);

        double minLongitude = calculateMinPoint(locationRangeRequest.longitude(), meterAssignedLevel, meterForLongitude);
        double maxLongitude = calculateMaxPoint(locationRangeRequest.longitude(), meterAssignedLevel, meterForLongitude);

        return new LocationSquareRangeCondition(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    private double calculateMeterForLatitude() {
        return (ONE_LATITUDE / Math.toRadians(EARTH_RADIUS)) / METER_PER_KILOMETER;
    }

    private double calculateMeterForLongitude(double longitude) {
        return (ONE_LONGITUDE / (Math.toRadians(EARTH_RADIUS) * Math.cos(Math.toRadians(longitude)))) / METER_PER_KILOMETER;
    }

    private double calculateMaxPoint(double point, int radiusMeter, double meterForPoint) {
        return point + (radiusMeter * meterForPoint);
    }

    private double calculateMinPoint(double point, int radiusMeter, double meterForPoint) {
        return point - (radiusMeter * meterForPoint);
    }

}
