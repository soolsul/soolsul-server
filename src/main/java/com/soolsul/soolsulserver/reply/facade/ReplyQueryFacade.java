package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.business.ReplyQueryService;
import com.soolsul.soolsulserver.reply.common.dto.response.ReplyDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyQueryFacade {

    private final ReplyQueryService replyQueryService;

    public Slice<ReplyDetailResponse> findReplies(String postId, Pageable pageable) {
        return replyQueryService.findReplies(postId, pageable);
    }
}
