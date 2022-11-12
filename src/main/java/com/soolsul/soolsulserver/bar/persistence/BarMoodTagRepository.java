package com.soolsul.soolsulserver.bar.persistence;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.soolsul.soolsulserver.bar.domain.QBarMoodTag.barMoodTag;
import static com.soolsul.soolsulserver.bar.domain.QMoodTag.moodTag;

@Repository
@RequiredArgsConstructor
public class BarMoodTagRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<String> findBarMoodTagIdsByMoodNames(List<String> moodTagNames) {
        JPQLQuery<String> moodTagIds = JPAExpressions.select(moodTag.id)
                .from(moodTag)
                .where(moodTag.name.in(moodTagNames));

        return jpaQueryFactory.select(barMoodTag.id)
                .from(barMoodTag)
                .where(barMoodTag.moodId.in(moodTagIds))
                .groupBy(barMoodTag.barId)
                .orderBy(OrderByNull.DEFAULT)
                .fetch();
    }

}
