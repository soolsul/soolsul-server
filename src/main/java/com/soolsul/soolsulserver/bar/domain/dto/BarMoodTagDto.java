package com.soolsul.soolsulserver.bar.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BarMoodTagDto {

    private String name;
    private boolean isCuration;
    private Integer count;

    @QueryProjection
    public BarMoodTagDto(String name, boolean isCuration, Integer count) {
        this.name = name;
        this.isCuration = isCuration;
        this.count = count;
    }
}
