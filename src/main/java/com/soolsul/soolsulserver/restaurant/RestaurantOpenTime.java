package com.soolsul.soolsulserver.restaurant;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import com.soolsul.soolsulserver.common.domain.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantOpenTime extends BaseTimeEntity {

    @Column(nullable = false)
    private String restaurantId;

    @Embedded
    private Duration duration;

    public RestaurantOpenTime(String restaurantId, Duration duration) {
        this.restaurantId = restaurantId;
        this.duration = duration;
    }
}
