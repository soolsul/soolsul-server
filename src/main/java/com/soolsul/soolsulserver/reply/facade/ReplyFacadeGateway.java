package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.common.dto.request.ReplyCreateRequest;
import com.soolsul.soolsulserver.reply.common.dto.request.ReplyModifyRequest;
import com.soolsul.soolsulserver.reply.common.dto.response.ReplyDetailResponse;
import com.soolsul.soolsulserver.reply.common.dto.response.PostRepliesResponse;
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
    public void create(String userId, String postId, ReplyCreateRequest request) {
        replyCommandFacade.create(userId, postId, request);
    }

    @Override
    public void delete(String userId, String postId, String replyId) {
        replyCommandFacade.delete(userId, postId, replyId);
    }

    @Override
    public void modify(String userId, String postId, String replyId, ReplyModifyRequest request) {
        replyCommandFacade.modify(userId, postId, replyId, request);
    }


    @Override
    public PostRepliesResponse findReplies(String postId, Pageable pageable) {
        Slice<ReplyDetailResponse> replies = replyQueryFacade.findReplies(postId, pageable);
        return new PostRepliesResponse(replies);
    }
}
