package com.soolsul.soolsulserver.domain.menu;

import com.soolsul.soolsulserver.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Snack extends BaseTimeEntity {

    private String name;
    private Long snackCategoryId;
}
