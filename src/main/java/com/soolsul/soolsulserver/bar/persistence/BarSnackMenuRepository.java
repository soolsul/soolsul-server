package com.soolsul.soolsulserver.bar.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.bar.common.dto.response.BarSnackMenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.soolsul.soolsulserver.menu.snack.domain.QSnack.snack;
import static com.soolsul.soolsulserver.menu.snack.domain.QSnackMenu.snackMenu;

@Repository
@RequiredArgsConstructor
public class BarSnackMenuRepository {

    private static final int LIMIT_NUMBER = 3;

    private final JPAQueryFactory jpaQueryFactory;

    public List<BarSnackMenuResponse> findAllBarSnackMenuByBarId(String barId) {
        return jpaQueryFactory.select(
                Projections.constructor(BarSnackMenuResponse.class,
                        snack.name.as("snackMenuName"),
                                snackMenu.cost))
                .from(snackMenu)
                .innerJoin(snack).on(snackMenu.snack.id.eq(snack.id))
                .where(snackMenu.barId.eq(barId))
                .limit(LIMIT_NUMBER)
                .fetch();
    }

}
