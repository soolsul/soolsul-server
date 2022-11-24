package com.soolsul.soolsulserver.reply.domain.query;

import com.soolsul.soolsulserver.reply.business.dto.response.ReplyDetailResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReplyQueryRepository {
    Slice<ReplyDetailResponse> findRepliesWithPage(String postId, Pageable pageable);
}
