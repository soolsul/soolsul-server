package com.soolsul.soolsulserver.reply.facade;

import com.soolsul.soolsulserver.reply.presentation.dto.response.PostRepliesResponse;
import org.springframework.data.domain.Pageable;

public interface ReplyQueryFacadeSpec {
    PostRepliesResponse findReplies(String postId, Pageable pageable);
}
