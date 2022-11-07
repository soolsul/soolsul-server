package com.soolsul.soolsulserver.restaurant;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import com.soolsul.soolsulserver.region.Location;
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

    public Restaurant(String regionId, String restaurantCategoryId, Location location) {
        this.regionId = regionId;
        this.restaurantCategoryId = restaurantCategoryId;
        this.location = location;
    }
}
