package com.soolsul.soolsulserver.domain.restaurant;

import com.soolsul.soolsulserver.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class restaurantMoodTag extends BaseEntity {

    @Column(nullable = false)
    private String restaurantId;

    @Column(nullable = false)
    private String name;

    private boolean isCuration;

    private Integer count;

    public restaurantMoodTag(String restaurantId, String name, boolean isCuration, Integer count) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.isCuration = isCuration;
        this.count = count;
    }
}
