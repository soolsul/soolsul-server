package com.soolsul.soolsulserver.common.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Category extends BaseTimeEntity{
    private String name;

    public Category(String name) {
        this.name = name;
    }

}
