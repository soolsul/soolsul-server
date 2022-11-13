package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarAlcoholTag extends BaseEntity {

    @Column(nullable = false)
    private String barId;

    @Column(nullable = false)
    private String alcoholCategoryId;

    @Column(nullable = false)
    private String alcoholCategoryName;

    public BarAlcoholTag(String barId, String alcoholCategoryId, String alcoholCategoryName) {
        this.barId = barId;
        this.alcoholCategoryId = alcoholCategoryId;
        this.alcoholCategoryName = alcoholCategoryName;
    }

}
