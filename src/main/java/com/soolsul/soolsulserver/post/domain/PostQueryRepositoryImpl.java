package com.soolsul.soolsulserver.post.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.post.domain.dto.FilteredPostLookupResponse;
import com.soolsul.soolsulserver.post.domain.dto.QFilteredPostLookupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.soolsul.soolsulserver.auth.QUserInfo.userInfo;
import static com.soolsul.soolsulserver.post.domain.QPost.post;

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
