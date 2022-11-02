package com.soolsul.soolsulserver.domain.restaurant;

import com.soolsul.soolsulserver.domain.common.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantCategory extends Category {}
