package com.soolsul.soolsulserver.curation.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Curation extends BaseTimeEntity {

    private String title;

    private String mainPictureUrl;

    private String content;

    private String barId;
}

