package com.soolsul.soolsulserver.reply.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.reply.common.dto.request.PostReplyRequest;
import com.soolsul.soolsulserver.reply.common.dto.response.PostRepliesResponse;
import com.soolsul.soolsulserver.reply.facade.ReplyFacadeGateway;
import com.soolsul.soolsulserver.user.auth.annotation.CurrentUser;
import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

import static com.soolsul.soolsulserver.common.response.ResponseCodes.REPLY_CREATE_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.REPLY_READ_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/replies")
public class ReplyController {

    private final ReplyFacadeGateway replyFacadeGateway;
    private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createReply(
            @Valid @RequestBody PostReplyRequest request,
            @PathVariable String postId,
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        replyFacadeGateway.create(currentUser.getId(), postId, request);

        String message = messageSource.getMessage(REPLY_CREATE_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<Void> baseResponse = new BaseResponse<>(
                REPLY_CREATE_SUCCESS.getCode(),
                message,
                null
        );

        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PostRepliesResponse>> findReplies(
            @PathVariable String postId,
            @PageableDefault(size = 10) Pageable pageable,
            Locale locale
    ) {
        PostRepliesResponse repliesResponse = replyFacadeGateway.findReplies(postId, pageable);

        String message = messageSource.getMessage(REPLY_READ_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<PostRepliesResponse> baseResponse = new BaseResponse<>(
                REPLY_READ_SUCCESS.getCode(),
                message,
                repliesResponse
        );

        return ResponseEntity.ok(baseResponse);
    }
}
