package com.soolsul.soolsulserver.menu.alcohol.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.soolsul.soolsulserver.menu.alcohol.domain.QAlcoholCategory.alcoholCategory;

@Repository
@RequiredArgsConstructor
public class AlcoholCategoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<String> findAlcoholCategoryIdsByAlcoholCategoryNames(List<String> alcoholCategoryNames) {
        return jpaQueryFactory.select(alcoholCategory.id)
                .from(alcoholCategory)
                .where(alcoholCategory.name.in(alcoholCategoryNames))
                .fetch();
    }

}
