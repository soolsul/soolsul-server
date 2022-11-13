package com.soolsul.soolsulserver.menu.alcohol.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlcoholMenu extends BaseTimeEntity {

    private double cost;

    private String alcoholId;

    private String barId;

}
