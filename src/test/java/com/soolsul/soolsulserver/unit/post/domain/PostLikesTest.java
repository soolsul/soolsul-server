package com.soolsul.soolsulserver.unit.post.domain;

import com.soolsul.soolsulserver.auth.User;
import com.soolsul.soolsulserver.post.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

public class PostLikesTest {

    @DisplayName("사용자는 Post에 좋아요를 누를 수 있습니다")
    @Test
    public void add_like_test() {
        // given
        User user = new User("user_id", "test@gamil.com", "1234", null);
        Post post = new Post("temp_ip", "bar_id", 4.3f, "contents");

        // when
        post.like(user);

        // then
        assertAll(
                () -> Assertions.assertThat(post.isLikeContain(user.getId())).isTrue(),
                () -> Assertions.assertThat(post.likeCount()).isEqualTo(1)
        );
    }

    @DisplayName("사용자는 Post에 좋아요를 지울 수 있습니다")
    @Test
    public void delete_like_test() {
        // given
        User user = new User("user_id", "test@gamil.com", "1234", null);
        Post post = new Post("temp_ip", "bar_id", 4.3f, "contents");

        // when
        post.undoLike(user);

        // then
        assertAll(
                () -> Assertions.assertThat(post.isLikeContain(user.getId())).isFalse(),
                () -> Assertions.assertThat(post.likeCount()).isEqualTo(0)
        );
    }

    @DisplayName("좋아요 숫자 테스트")
    @Test
    public void like_count_test() {
        // given
        User user1 = new User("user_id1", "test@gamil.com", "1234", null);
        User user2 = new User("user_id2", "test@gamil.com", "1234", null);
        User user3 = new User("user_id3", "test@gamil.com", "1234", null);
        Post post = new Post("temp_ip", "bar_id", 4.3f, "contents");

        // when
        post.like(user1);
        post.like(user2);
        post.like(user3);

        // then
        Assertions.assertThat(post.likeCount()).isEqualTo(3);
    }

    @DisplayName("동일한 사용자가 같은 피드에 좋아요를 여러번 눌러도 한번만 카운트 된다")
    @Test
    public void user_can_have_only_one_like_count_test() {
        // given
        User user1 = new User("user_id1", "test@gamil.com", "1234", null);
        User user2 = new User("user_id2", "test@gamil.com", "1234", null);
        Post post = new Post("temp_ip", "bar_id", 4.3f, "contents");

        // when
        post.like(user1);
        post.like(user1);
        post.like(user1);
        post.like(user2);
        post.like(user2);

        // then
        assertAll(
                () -> Assertions.assertThat(post.isLikeContain(user1.getId())).isTrue(),
                () -> Assertions.assertThat(post.isLikeContain(user2.getId())).isTrue(),
                () -> Assertions.assertThat(post.likeCount()).isEqualTo(2)
        );
    }
}
