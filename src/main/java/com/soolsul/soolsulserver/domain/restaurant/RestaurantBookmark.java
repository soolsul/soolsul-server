package com.soolsul.soolsulserver.domain.restaurant;

import com.soolsul.soolsulserver.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
