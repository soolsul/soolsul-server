package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarMoodTag extends BaseEntity {

    @Column(nullable = false)
    private String barId;

    @Column(nullable = false)
    private String name;

    private boolean isCuration;

    private Integer count;

    public BarMoodTag(String barId, String name, boolean isCuration, Integer count) {
        this.barId = barId;
        this.name = name;
        this.isCuration = isCuration;
        this.count = count;
    }
}
