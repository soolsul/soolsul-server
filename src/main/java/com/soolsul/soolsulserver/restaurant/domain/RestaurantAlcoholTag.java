package com.soolsul.soolsulserver.restaurant.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantAlcoholTag extends BaseEntity {

    @Column(nullable = false)
    private String restaurantId;

    @Column(nullable = false)
    private String name;

    public RestaurantAlcoholTag(String restaurantId, String name) {
        this.restaurantId = restaurantId;
        this.name = name;
    }
}
