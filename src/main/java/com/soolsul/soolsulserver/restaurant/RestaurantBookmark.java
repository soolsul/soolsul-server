package com.soolsul.soolsulserver.restaurant;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantBookmark extends BaseTimeEntity {

    @Column(nullable = false)
    private String userInfoId;

    @Column(nullable = false)
    private String restaurantId;

    public RestaurantBookmark(String userInfoId, String restaurantId) {
        this.userInfoId = userInfoId;
        this.restaurantId = restaurantId;
    }
}
