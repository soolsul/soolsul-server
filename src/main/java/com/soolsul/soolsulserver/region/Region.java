package com.soolsul.soolsulserver.region;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String name;
    @Embedded
    private Location location;
    private Integer depth;
    private String parentId;
}
