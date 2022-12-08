package com.soolsul.soolsulserver.reply.domain.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.post.common.dto.response.QUserReplyLookUpResponse;
import com.soolsul.soolsulserver.post.common.dto.response.UserReplyLookUpResponse;
import com.soolsul.soolsulserver.reply.common.dto.response.QReplyDetailResponse;
import com.soolsul.soolsulserver.reply.common.dto.response.ReplyDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.soolsul.soolsulserver.reply.domain.QReply.reply;
import static com.soolsul.soolsulserver.user.auth.QCustomUser.customUser;
import static com.soolsul.soolsulserver.user.auth.QUserInfo.userInfo;

@RequiredArgsConstructor
public class ReplyQueryRepositoryImpl implements ReplyQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ReplyDetailResponse> findRepliesWithPage(String postId, Pageable pageable) {
        List<ReplyDetailResponse> replies = queryFactory
                .select(
                        new QReplyDetailResponse(
                                reply.id,
                                reply.contents.reviewContent,
                                customUser.id,
                                userInfo.nickname,
                                userInfo.profileImage)
                )
                .from(customUser)
                .innerJoin(userInfo).on(customUser.id.eq(userInfo.userId))
                .innerJoin(reply).on(customUser.id.eq(reply.ownerId))
                .where(reply.postId.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return checkEndPage(replies, pageable);
    }

    @Override
    public List<UserReplyLookUpResponse> findRepliesByUserId(String userId) {
        return queryFactory
                .select(new QUserReplyLookUpResponse(reply.postId, reply.contents.reviewContent))
                .from(reply)
                .where(reply.ownerId.eq(userId))
                .fetch();
    }

    private Slice<ReplyDetailResponse> checkEndPage(List<ReplyDetailResponse> results, Pageable pageable) {
        if (hasNextPage(results, pageable)) {
            results.remove(pageable.getPageSize());
            return new SliceImpl<>(results, pageable, true);
        }

        return new SliceImpl<>(results, pageable, false);
    }

    private boolean hasNextPage(List<ReplyDetailResponse> results, Pageable pageable) {
        return results.size() > pageable.getPageSize();
    }
}
