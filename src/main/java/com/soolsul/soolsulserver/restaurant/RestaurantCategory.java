package com.soolsul.soolsulserver.restaurant;

import com.soolsul.soolsulserver.common.domain.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantCategory extends Category {}