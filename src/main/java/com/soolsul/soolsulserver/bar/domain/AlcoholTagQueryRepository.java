package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.bar.domain.dto.BarAlcoholTagDto;

import java.util.List;

public interface AlcoholTagQueryRepository {

    List<BarAlcoholTagDto> findAllByPostId(String id);
}
