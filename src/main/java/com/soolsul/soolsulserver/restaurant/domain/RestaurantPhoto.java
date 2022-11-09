package com.soolsul.soolsulserver.restaurant.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantPhoto extends BaseEntity {

    @Column(nullable = false)
    private String restaurantId;

    private String originalFileName;
    private String uuidFileUrl;
    private String extension;

    public RestaurantPhoto(String restaurantId, String originalFileName, String uuidFileUrl, String extension) {
        this.restaurantId = restaurantId;
        this.originalFileName = originalFileName;
        this.uuidFileUrl = uuidFileUrl;
        this.extension = extension;
    }
}
