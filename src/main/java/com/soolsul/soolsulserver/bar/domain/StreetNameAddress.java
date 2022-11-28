package com.soolsul.soolsulserver.bar.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreetNameAddress {

    private static final String ROAD_NAME_SUFFIX = "길";
    @ColumnDefault("''")
    private String regionName = "";

    @ColumnDefault("''")
    private String city = "";

    @ColumnDefault("''")
    private String district = "";

    @ColumnDefault("''")
    private String roadName = "";

    private int roadNumber;

    @ColumnDefault("''")
    private String roadNumberDetail = "";

    @ColumnDefault("''")
    private String locationDetail = "";

    public StreetNameAddress(
            String regionName,
            String city,
            String district,
            String roadName,
            int roadNumber,
            String roadNumberDetail,
            String locationDetail
    ) {
        this.regionName = regionName;
        this.city = city;
        this.district = district;
        this.roadName = roadName;
        this.roadNumber = roadNumber;
        this.roadNumberDetail = roadNumberDetail;
        this.locationDetail = locationDetail;
    }

    public String simpleStreetNameAddress() {
        return String.format("%s %s %s", regionName, city, district).trim();
    }

    public String detailStreetNameAddress() {
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
