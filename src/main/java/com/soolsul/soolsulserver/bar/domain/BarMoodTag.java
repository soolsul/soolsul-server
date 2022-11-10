package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarMoodTag extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private boolean isCuration;

    private Integer count;

    public BarMoodTag(String name, boolean isCuration, Integer count) {
        this.name = name;
        this.isCuration = isCuration;
        this.count = count;
    }
}
