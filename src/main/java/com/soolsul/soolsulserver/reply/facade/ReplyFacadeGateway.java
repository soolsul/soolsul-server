package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.business.dto.response.ReplyDetailResponse;
import com.soolsul.soolsulserver.reply.presentation.dto.request.PostReplyRequest;
import com.soolsul.soolsulserver.reply.presentation.dto.response.PostRepliesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyFacadeGateway implements ReplyCommandFacadeSpec, ReplyQueryFacadeSpec {

    private final ReplyCommandFacade replyCommandFacade;
    private final ReplyQueryFacade replyQueryFacade;

    @Override
    public void create(String userId, String postId, PostReplyRequest request) {
        replyCommandFacade.create(userId, postId, request);
    }

    @Override
    public PostRepliesResponse findReplies(String postId, Pageable pageable) {
        Slice<ReplyDetailResponse> replies = replyQueryFacade.findReplies(postId, pageable);
        return new PostRepliesResponse(replies);
    }
}
