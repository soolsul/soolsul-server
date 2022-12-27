package com.soolsul.soolsulserver.reply.business;

import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.exception.PostNotFoundException;
import com.soolsul.soolsulserver.reply.common.dto.request.ReplyModifyRequest;
import com.soolsul.soolsulserver.reply.domain.Reply;
import com.soolsul.soolsulserver.reply.domain.ReplyRepository;
import com.soolsul.soolsulserver.reply.exception.ReplyNotFoundException;
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

    /**
     * Delete는 멱등해야 하는 API이기 때문에 조건에 부합하지 않아도 예외를 던지지 않는다.
     * findById로 찾아온 reply가 없다면 이경우 예외를 던지며, Handler에서 멱등하게 반환을 시켜준다.
     */
    public void delete(String userId, String postId, String replyId) {
        Reply findReply = replyRepository.findById(replyId)
                .orElseThrow(ReplyNotFoundException::new);

        if (findReply.isOwner(userId) && findReply.isSamePostId(postId)) {
            replyRepository.deleteById(replyId);
        }
    }

    public void modify(String userId, String postId, String replyId, ReplyModifyRequest request) {
        Reply findReply = replyRepository.findById(replyId)
                .orElseThrow(ReplyNotFoundException::new);

        if (findReply.isOwner(userId) && findReply.isSamePostId(postId)) {
            findReply.modifyContents(request.contents());
        }
    }
}
