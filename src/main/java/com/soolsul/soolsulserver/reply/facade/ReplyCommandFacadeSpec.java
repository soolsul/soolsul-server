package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.common.dto.request.ReplyCreateRequest;
import com.soolsul.soolsulserver.reply.common.dto.request.ReplyModifyRequest;

public interface ReplyCommandFacadeSpec {
    void create(String userId, String postId, ReplyCreateRequest request);

    void delete(String userId, String postId, String replyId);

    void modify(String userId, String postId, String replyId, ReplyModifyRequest request);
}
