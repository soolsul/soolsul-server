package com.soolsul.soolsulserver.menu;

import com.soolsul.soolsulserver.common.domain.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlcoholCategory extends Category { }
