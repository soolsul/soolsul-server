package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.business.ReplyCommandService;
import com.soolsul.soolsulserver.reply.presentation.dto.request.PostReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ReplyCommandFacade {

    private final ReplyCommandService replyCommandService;

    public void create(String userId, String postId, PostReplyRequest request) {
        replyCommandService.create(userId, postId, request.contents());
    }
}
