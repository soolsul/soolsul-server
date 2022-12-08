package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.common.dto.response.PostRepliesResponse;
import org.springframework.data.domain.Pageable;

public interface ReplyQueryFacadeSpec {
    PostRepliesResponse findReplies(String postId, Pageable pageable);
}
