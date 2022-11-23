package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.presentation.dto.request.PostReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyFacadeGateway implements ReplyCommandFacadeSpec {

    private final ReplyCommandFacade replyCommandFacade;

    @Override
    public void create(String userId, String postId, PostReplyRequest request) {
        replyCommandFacade.create(userId, postId, request);
    }
}
