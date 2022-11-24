package com.soolsul.soolsulserver.reply.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.reply.facade.ReplyFacadeGateway;
import com.soolsul.soolsulserver.reply.presentation.dto.request.PostReplyRequest;
import com.soolsul.soolsulserver.user.auth.CurrentUser;
import com.soolsul.soolsulserver.user.auth.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/replies")
public class ReplyController {

    private final ReplyFacadeGateway replyFacadeGateway;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createReply(@Valid @RequestBody PostReplyRequest request, @PathVariable String postId, @CurrentUser CustomUser currentUser) {
        replyFacadeGateway.create(currentUser.getId(), postId, request);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.REPLY_CREATE_SUCCESS, null));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Void>> findReplies(@PathVariable String postId, @CurrentUser CustomUser currentUser) {
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.REPLY_CREATE_SUCCESS, null));
    }
}
