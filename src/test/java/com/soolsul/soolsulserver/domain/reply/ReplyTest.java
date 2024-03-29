package com.soolsul.soolsulserver.domain.reply;

import com.soolsul.soolsulserver.domain.base.DomainTest;
import com.soolsul.soolsulserver.reply.domain.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DomainTest
class ReplyTest {

    @DisplayName("Reply 생성 테스트")
    @Test
    void reply_create_test() {
        assertThatCode(() -> new Reply("owner_id", "post_id", "contents"))
                .doesNotThrowAnyException();
    }

    @DisplayName("Reply Id가 같은 경우 equals를 통해 동일한 객체로 판단하는지 확인한다.")
    @Test
    void compare_equal_test() {
        Reply replyOne = createReply("1", "즐거운1");
        Reply replyTwo = createReply("1", "즐거운2");
        assertThat(replyOne).isEqualTo(replyTwo);
    }

    @DisplayName("Reply Id가 다른 경우 equals를 통해 다른 객체로 판단한다.")
    @Test
    void compare_not_equal_test() {
        Reply replyOne = createReply("1", "즐거운1");
        Reply replyTwo = createReply("2", "즐거운1");
        assertThat(replyOne).isNotEqualTo(replyTwo);
    }

    private Reply createReply(String id, String contents) {
        Reply reply = new Reply("owner_id", "post_id", contents);
        ReflectionTestUtils.setField(reply, "id", id);
        return reply;
    }
}
