package com.soolsul.soolsulserver.menu;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Snack extends BaseTimeEntity {

    private String name;
    private Long snackCategoryId;
}
