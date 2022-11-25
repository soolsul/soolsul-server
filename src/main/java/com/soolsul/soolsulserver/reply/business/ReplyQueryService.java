package com.soolsul.soolsulserver.reply.business;

import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.exception.PostNotFoundException;
import com.soolsul.soolsulserver.reply.business.dto.response.ReplyDetailResponse;
import com.soolsul.soolsulserver.reply.domain.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyQueryService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    public Slice<ReplyDetailResponse> findReplies(String postId, Pageable pageable) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return replyRepository.findRepliesWithPage(findPost.getId(), pageable);
    }
}
