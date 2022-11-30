package com.soolsul.soolsulserver.bar.businees.dto;

import lombok.Getter;

@Getter
public class BarStreetNameAddressResponse {

    private static final String ROAD_NAME_SUFFIX = "길";

    private String simpleStreetNameAddress;

    private String detailStreetNameAddress;

    public BarStreetNameAddressResponse(
            String regionName,
            String city,
            String district,
            String roadName,
            int roadNumber,
            String roadNumberDetail,
            String locationDetail
    ) {
        this.simpleStreetNameAddress = simpleStreetNameAddress(regionName, city, district);
        this.detailStreetNameAddress = detailStreetNameAddress(
                regionName,
                city,
                district,
                roadName,
                roadNumber,
                roadNumberDetail,
                locationDetail
        );
    }

    public String simpleStreetNameAddress(String regionName, String city, String district) {
        return String.format("%s %s %s", regionName, city, district).trim();
    }

    public String detailStreetNameAddress(
            String regionName,
            String city,
            String district,
            String roadName,
            int roadNumber,
            String roadNumberDetail,
            String locationDetail
    ) {
        return String.format("%s %s %s%s %s %s %s",
                        regionName,
                        city,
                        district,
                        roadName,
                        roadNumber == 0 ? "" : roadNumber + ROAD_NAME_SUFFIX,
                        roadNumberDetail,
                        locationDetail.isEmpty() ? "" : locationDetail)
                .trim();
    }

}
