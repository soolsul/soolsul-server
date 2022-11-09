package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import com.soolsul.soolsulserver.region.domain.Location;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseTimeEntity {

    private String regionId;
    private String restaurantCategoryId;

    @Embedded
    private Location location;

    public Restaurant(String id, String regionId, String restaurantCategoryId, Location location) {
        super(id);
        this.regionId = regionId;
        this.restaurantCategoryId = restaurantCategoryId;
        this.location = location;
    }

    public Restaurant(String regionId, String restaurantCategoryId, Location location) {
        this.regionId = regionId;
        this.restaurantCategoryId = restaurantCategoryId;
        this.location = location;
    }
}
