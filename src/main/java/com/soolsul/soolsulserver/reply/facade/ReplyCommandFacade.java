package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.business.ReplyCommandService;
import com.soolsul.soolsulserver.reply.common.dto.request.ReplyCreateRequest;
import com.soolsul.soolsulserver.reply.common.dto.request.ReplyModifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ReplyCommandFacade {

    private final ReplyCommandService replyCommandService;

    public void create(String userId, String postId, ReplyCreateRequest request) {
        replyCommandService.create(userId, postId, request.contents());
    }

    public void delete(String userId, String postId, String replyId) {
        replyCommandService.delete(userId, postId, replyId);
    }

    public void modify(String userId, String postId, String replyId, ReplyModifyRequest request) {
        replyCommandService.modify(userId, postId, replyId, request);
    }
}
