package com.soolsul.soolsulserver.reply.domain.query;

import com.soolsul.soolsulserver.post.domain.dto.UserReplyLookUpResponse;
import com.soolsul.soolsulserver.reply.business.dto.response.ReplyDetailResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ReplyQueryRepository {
    Slice<ReplyDetailResponse> findRepliesWithPage(String postId, Pageable pageable);

    List<UserReplyLookUpResponse> findRepliesByUserId(String userId);
}
