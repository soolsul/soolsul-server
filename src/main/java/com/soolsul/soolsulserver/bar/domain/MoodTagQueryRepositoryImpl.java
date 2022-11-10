package com.soolsul.soolsulserver.bar.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.bar.domain.dto.BarMoodTagDto;
import com.soolsul.soolsulserver.bar.domain.dto.QBarMoodTagDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.soolsul.soolsulserver.attach.domain.QAttach.attach;
import static com.soolsul.soolsulserver.bar.domain.QBarMoodTag.barMoodTag;

@RequiredArgsConstructor
public class MoodTagQueryRepositoryImpl implements MoodTagQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BarMoodTagDto> findAllByPostId(String postId) {
        return queryFactory
                .select(new QBarMoodTagDto(barMoodTag.name, barMoodTag.isCuration, barMoodTag.count))
                .from(barMoodTag)
                .where(barMoodTag.id.in(
                        JPAExpressions
                                .select(attach.tagId)
                                .from(attach)
                                .where(attach.postId.eq(postId))
                )).fetch();
    }
}
