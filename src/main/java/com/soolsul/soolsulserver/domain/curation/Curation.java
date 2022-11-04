package com.soolsul.soolsulserver.domain.curation;

import com.soolsul.soolsulserver.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Curation extends BaseTimeEntity {

    private String title;

    private String mainPictureUrl;

    private String content;

    private String restaurantId;
}

