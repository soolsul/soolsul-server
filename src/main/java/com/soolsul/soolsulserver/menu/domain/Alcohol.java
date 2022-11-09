package com.soolsul.soolsulserver.menu.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alcohol extends BaseTimeEntity {

    private String name;

    private int alcoholPercent;

    private Long alcoholCategoryId;
}
