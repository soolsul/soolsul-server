package com.soolsul.soolsulserver.restaurant;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class restaurantAlcoholTag extends BaseEntity {

    @Column(nullable = false)
    private String restaurantId;

    @Column(nullable = false)
    private String name;

    public restaurantAlcoholTag(String restaurantId, String name) {
        this.restaurantId = restaurantId;
        this.name = name;
    }
}
