package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarBookmark extends BaseTimeEntity {

    @Column(nullable = false)
    private String userInfoId;

    @Column(nullable = false)
    private String barId;

    public BarBookmark(String userInfoId, String barId) {
        this.userInfoId = userInfoId;
        this.barId = barId;
    }
}
