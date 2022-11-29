package com.soolsul.soolsulserver.post.domain.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.curation.dto.CurationPostLookupResponse;
import com.soolsul.soolsulserver.curation.dto.QCurationPostLookupResponse;
import com.soolsul.soolsulserver.curation.dto.QPostPhotoImageResponse;
import com.soolsul.soolsulserver.post.domain.dto.FilteredPostLookupResponse;
import com.soolsul.soolsulserver.post.domain.dto.QFilteredPostLookupResponse;
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
