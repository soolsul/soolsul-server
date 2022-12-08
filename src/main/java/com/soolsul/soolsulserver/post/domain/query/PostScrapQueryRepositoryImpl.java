package com.soolsul.soolsulserver.post.domain.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.post.common.dto.response.QScrapedPostLookUpResponse;
import com.soolsul.soolsulserver.post.common.dto.response.ScrapedPostLookUpResponse;
import com.soolsul.soolsulserver.post.domain.QPostPhoto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.soolsul.soolsulserver.post.domain.QPostPhoto.postPhoto;
import static com.soolsul.soolsulserver.post.domain.QPostScrap.postScrap;

@RequiredArgsConstructor
public class PostScrapQueryRepositoryImpl implements PostScrapQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ScrapedPostLookUpResponse> findAllScrapedPost(String userId) {
        QPostPhoto subPhoto = new QPostPhoto("subPhoto");

        JPQLQuery<Tuple> subQuery = JPAExpressions
                .select(subPhoto.post.id, subPhoto.uuidFileUrl.min())
                .from(subPhoto)
                .groupBy(subPhoto.post.id);

        return queryFactory
                .select(new QScrapedPostLookUpResponse(postScrap.postId, postPhoto.uuidFileUrl))
                .from(postScrap).join(postPhoto)
                .on(postScrap.postId.eq(postPhoto.post.id))
                .where(
                        postScrap.ownerId.eq(userId),
                        subQuery.having(postPhoto.uuidFileUrl.eq(subPhoto.uuidFileUrl.min())).exists()
                )
                .orderBy(postScrap.createdAt.desc())
                .fetch();
    }
}
