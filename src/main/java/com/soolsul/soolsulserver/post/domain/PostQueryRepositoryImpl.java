package com.soolsul.soolsulserver.post.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.soolsul.soolsulserver.post.domain.QPost.post;

@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Post> findPostListByLocation(List<String> barIds, Pageable pageable) {
        List<Post> postList = queryFactory
                .select(post)
                .from(post)
                .where(post.id.in(barIds))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return checkEndPage(postList, pageable);
    }

    private Slice<Post> checkEndPage(List<Post> results, Pageable pageable) {
        if (hasNextPage(results, pageable)) {
            results.remove(pageable.getPageSize()); //한개더 가져왔으니 더 가져온 데이터 삭제
            return new SliceImpl<>(results, pageable, true);
        }

        return new SliceImpl<>(results, pageable, false);
    }

    private boolean hasNextPage(List<Post> results, Pageable pageable) {
        return results.size() > pageable.getPageSize();
    }
}
