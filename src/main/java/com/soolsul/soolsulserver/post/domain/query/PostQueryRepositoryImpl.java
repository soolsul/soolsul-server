package com.soolsul.soolsulserver.post.domain.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.curation.dto.CurationPostLookupResponse;
import com.soolsul.soolsulserver.curation.dto.QCurationPostLookupResponse;
import com.soolsul.soolsulserver.curation.dto.QPostPhotoImageResponse;
import com.soolsul.soolsulserver.post.domain.QPostPhoto;
import com.soolsul.soolsulserver.post.domain.dto.FilteredPostLookupResponse;
import com.soolsul.soolsulserver.post.domain.dto.QFilteredPostLookupResponse;
import com.soolsul.soolsulserver.post.domain.dto.QUserPostLookUpResponse;
import com.soolsul.soolsulserver.post.domain.dto.UserPostLookUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.soolsul.soolsulserver.post.domain.QPost.post;
import static com.soolsul.soolsulserver.post.domain.QPostPhoto.postPhoto;
import static com.soolsul.soolsulserver.user.auth.QUserInfo.userInfo;

@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<FilteredPostLookupResponse> findPostListByLocation(List<String> barIds, Pageable pageable) {
        List<FilteredPostLookupResponse> postList = queryFactory
                .select(new QFilteredPostLookupResponse(post, userInfo))
                .from(post).innerJoin(userInfo)
                .on(post.ownerId.eq(userInfo.userId))
                .where(post.barId.in(barIds))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return checkEndPage(postList, pageable);
    }

    @Override
    public List<CurationPostLookupResponse> findAllPostByBarId(String barId) {
        return queryFactory
                .from(post)
                .leftJoin(postPhoto).on(post.id.eq(postPhoto.post.id))
                .innerJoin(userInfo).on(userInfo.userId.eq(post.ownerId))
                .where(post.barId.eq(barId))
                .transform(
                        groupBy(post.id).list(new QCurationPostLookupResponse(
                                        post.contents.as("content"),
                                        userInfo.name.as("userName"),
                                        list(new QPostPhotoImageResponse(postPhoto.uuidFileUrl).as("postPhotoImageUrl")),
                                        post.likes.likeUsers.size().as("userLikes")))
                );

    public List<UserPostLookUpResponse> findAllUserPost(String userId) {
        QPostPhoto subPhoto = new QPostPhoto("subPhoto");

        JPQLQuery<Tuple> subQuery = JPAExpressions
                .select(subPhoto.post.id, subPhoto.uuidFileUrl.min())
                .from(subPhoto)
                .groupBy(subPhoto.post.id)
                .having(postPhoto.uuidFileUrl.eq(subPhoto.uuidFileUrl.min()));

        return queryFactory
                .select(new QUserPostLookUpResponse(post.id, postPhoto.uuidFileUrl))
                .from(post).join(postPhoto)
                .on(post.id.eq(postPhoto.post.id))
                .where(
                        post.ownerId.eq(userId),
                        subQuery.exists()
                )
                .orderBy(post.createdAt.desc())
                .fetch();
    }

    private Slice<FilteredPostLookupResponse> checkEndPage(List<FilteredPostLookupResponse> results, Pageable pageable) {
        if (hasNextPage(results, pageable)) {
            results.remove(pageable.getPageSize()); //한개더 가져왔으니 더 가져온 데이터 삭제
            return new SliceImpl<>(results, pageable, true);
        }

        return new SliceImpl<>(results, pageable, false);
    }

    private boolean hasNextPage(List<FilteredPostLookupResponse> results, Pageable pageable) {
        return results.size() > pageable.getPageSize();
    }
}
