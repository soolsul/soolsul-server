package com.soolsul.soolsulserver.reply.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.reply.common.dto.request.ReplyCreateRequest;
import com.soolsul.soolsulserver.reply.common.dto.request.ReplyModifyRequest;
import com.soolsul.soolsulserver.reply.common.dto.response.PostRepliesResponse;
import com.soolsul.soolsulserver.reply.facade.ReplyFacadeGateway;
import com.soolsul.soolsulserver.user.auth.annotation.CurrentUser;
import com.soolsul.soolsulserver.user.auth.vo.CurrentUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<BaseResponse<Void>> createReply(
            @Valid @RequestBody ReplyCreateRequest request,
            @PathVariable String postId,
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        replyFacadeGateway.create(currentUserDto.id(), postId, request);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.REPLY_CREATE_SUCCESS, null));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PostRepliesResponse>> findReplies(
            @PathVariable String postId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        PostRepliesResponse repliesResponse = replyFacadeGateway.findReplies(postId, pageable);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.REPLY_READ_SUCCESS, repliesResponse));
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<BaseResponse<Void>> modifyReply(
            @PathVariable String postId,
            @PathVariable String replyId,
            @RequestBody ReplyModifyRequest modifyRequest,
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        replyFacadeGateway.modify(currentUserDto.id(), postId, replyId, modifyRequest);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.REPLY_UPDATE_SUCCESS, null));
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<BaseResponse<Void>> deleteReply(
            @PathVariable String postId,
            @PathVariable String replyId,
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        replyFacadeGateway.delete(currentUserDto.id(), postId, replyId);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.REPLY_DELETE_SUCCESS, null));
    }
}
