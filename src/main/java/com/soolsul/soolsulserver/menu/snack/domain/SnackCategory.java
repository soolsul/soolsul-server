package com.soolsul.soolsulserver.menu.snack.domain;

import com.soolsul.soolsulserver.common.domain.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnackCategory extends Category {}
