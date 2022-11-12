package com.soolsul.soolsulserver.bar.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.soolsul.soolsulserver.bar.domain.QBarAlcoholTag.barAlcoholTag;

@Repository
@RequiredArgsConstructor
public class BarAlcoholTagQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<String> findBarAlcoholTagIdsByAlcoholCategoryIds(List<String> alcoholCategoryIds) {
        return jpaQueryFactory.select(barAlcoholTag.id)
                .from(barAlcoholTag)
                .where(barAlcoholTag.alcoholCategoryId.in(alcoholCategoryIds))
                .fetch();
    }

}
