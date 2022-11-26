package com.soolsul.soolsulserver.reply.persistence;

import com.soolsul.soolsulserver.config.QueryDslConfig;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.reply.business.dto.response.ReplyDetailResponse;
import com.soolsul.soolsulserver.reply.domain.Reply;
import com.soolsul.soolsulserver.reply.domain.ReplyRepository;
import com.soolsul.soolsulserver.user.auth.CustomUser;
import com.soolsul.soolsulserver.user.auth.UserInfo;
import com.soolsul.soolsulserver.user.auth.presentation.dto.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(QueryDslConfig.class)
@DataJpaTest(
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ReplyRepository.class)
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReplyPersistenceTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Rollback(value = false)
    @DisplayName("해당 피드에 추가되어 있는 댓글을 찾아온다.")
    @Test
    public void find_all_replies_in_post_test() {
        // given
        Post savedPost = testEntityManager.persist(new Post("test_user_id", "bar_id", 4.3f, "contents"));
        CustomUser savedUser = testEntityManager.persist(new CustomUser("custom_user", "1234"));
        testEntityManager.persist(UserInfo.of(savedUser.getId(), new UserRegisterRequest("email", "1234", "010-1234-5678", "name", "shine")));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_1"));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_2"));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_3"));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_4"));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_5"));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_6"));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_7"));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_8"));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_9"));
        testEntityManager.persist(new Reply(savedUser.getId(), savedPost.getId(), "content_10"));

        // when
        PageRequest pageRequest = PageRequest.of(1, 4);
        Slice<ReplyDetailResponse> repliesWithPage = replyRepository.findRepliesWithPage(savedPost.getId(), pageRequest);

        // then
        assertAll(
                () -> assertThat(repliesWithPage.getSize()).isEqualTo(4),
                () -> assertThat(repliesWithPage.getContent()).extracting("contents")
                        .containsExactly("content_5", "content_6", "content_7", "content_8")
        );
    }
}
