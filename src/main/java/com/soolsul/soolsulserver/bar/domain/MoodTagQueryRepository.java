package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.bar.domain.dto.BarMoodTagDto;

import java.util.List;

public interface MoodTagQueryRepository {

    List<BarMoodTagDto> findAllByPostId(String id);
}
