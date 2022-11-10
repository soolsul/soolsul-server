package com.soolsul.soolsulserver.bar.domain;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.bar.domain.dto.BarAlcoholTagDto;
import com.soolsul.soolsulserver.bar.domain.dto.QBarAlcoholTagDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.soolsul.soolsulserver.attach.domain.QAttach.attach;
import static com.soolsul.soolsulserver.bar.domain.QBarAlcoholTag.barAlcoholTag;

@RequiredArgsConstructor
public class AlcoholTagQueryRepositoryImpl implements AlcoholTagQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BarAlcoholTagDto> findAllByPostId(String postId) {
        return queryFactory
                .select(new QBarAlcoholTagDto(barAlcoholTag.name))
                .from(barAlcoholTag)
                .where(barAlcoholTag.id.in(
                        JPAExpressions
                                .select(attach.tagId)
                                .from(attach)
                                .where(attach.postId.eq(postId))
                )).fetch();
    }
}
