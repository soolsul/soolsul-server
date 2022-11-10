package com.soolsul.soolsulserver.bar.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BarAlcoholTagDto {

    private String name;

    @QueryProjection
    public BarAlcoholTagDto(String name) {
        this.name = name;
    }
}
