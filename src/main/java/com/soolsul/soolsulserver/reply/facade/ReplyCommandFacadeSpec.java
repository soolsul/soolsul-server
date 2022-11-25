package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.presentation.dto.request.PostReplyRequest;

public interface ReplyCommandFacadeSpec {
    void create(String userId, String postId, PostReplyRequest request);
}
