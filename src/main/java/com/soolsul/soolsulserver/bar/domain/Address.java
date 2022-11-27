package com.soolsul.soolsulserver.bar.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private static final String BLANK = " ";
    private String district; //도

    private String city; // 시, 군

    private String section; // 구

    private String road; //도로

    private String roadNumber; //도로명

    private String details; // 상세 주소

    public Address(String district, String city, String section, String road, String roadNumber, String details) {
        this.district = district;
        this.city = city;
        this.section = section;
        this.road = road;
        this.roadNumber = roadNumber;
        this.details = details;
    }

    public String addressDetails() {
        return String.format("%s %s %s %s %s %s", district, city, section, road, roadNumber, details).trim();
    }

}
