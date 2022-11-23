package com.soolsul.soolsulserver.reply.business;

import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.exception.PostNotFoundException;
import com.soolsul.soolsulserver.reply.domain.Reply;
import com.soolsul.soolsulserver.reply.domain.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyCommandService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    public void create(String userId, String postId, String contents) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Reply reply = new Reply(userId, findPost.getId(), contents);

        replyRepository.save(reply);
    }
}
