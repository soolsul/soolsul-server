package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import com.soolsul.soolsulserver.common.domain.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarOpenTime extends BaseTimeEntity {

    @Column(nullable = false)
    private String barId;

    @Embedded
    private Duration duration;

    public BarOpenTime(String barId, Duration duration) {
        this.barId = barId;
        this.duration = duration;
    }
}
