package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import com.soolsul.soolsulserver.region.domain.Location;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bar extends BaseTimeEntity {

    private String regionId;

    private String barCategoryId;

    private String name;

    private String description;

    @Embedded
    private Location location;

    public Bar(String id, String regionId, String barCategoryId, Location location) {
        super(id);
        this.regionId = regionId;
        this.barCategoryId = barCategoryId;
        this.location = location;
    }

    public Bar(String regionId, String barCategoryId, Location location) {
        this.regionId = regionId;
        this.barCategoryId = barCategoryId;
        this.location = location;
    }
}
