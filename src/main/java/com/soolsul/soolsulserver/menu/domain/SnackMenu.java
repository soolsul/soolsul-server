package com.soolsul.soolsulserver.menu.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnackMenu extends BaseTimeEntity {

    private Long cost;
    private String snackId;
    private String restaurantId;
}
