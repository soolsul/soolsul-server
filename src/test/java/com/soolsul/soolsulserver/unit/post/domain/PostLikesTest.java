package com.soolsul.soolsulserver.unit.post.domain;

import com.soolsul.soolsulserver.auth.Authority;
import com.soolsul.soolsulserver.auth.CustomUser;
import com.soolsul.soolsulserver.auth.Role;
import com.soolsul.soolsulserver.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PostLikesTest {

    @DisplayName("사용자는 Post에 좋아요를 누를 수 있습니다")
    @Test
    public void add_like_test() {
        // given
        CustomUser customUser = new CustomUser("test@email.com", "1234");
        Post post = new Post("temp_ip", "bar_id", 4.3f, "contents");

        // when
        post.like(customUser);

        // then
        assertAll(
                () -> assertThat(post.isLikeContain(customUser.getId())).isTrue(),
                () -> assertThat(post.likeCount()).isEqualTo(1)
        );
    }

    @DisplayName("사용자는 Post에 좋아요를 지울 수 있습니다")
    @Test
    public void delete_like_test() {
        // given
        CustomUser customUser = new CustomUser("test@email.com", "1234");
        Post post = new Post("temp_ip", "bar_id", 4.3f, "contents");

        // when
        post.undoLike(customUser);

        // then
        assertAll(
                () -> assertThat(post.isLikeContain(customUser.getId())).isFalse(),
                () -> assertThat(post.likeCount()).isEqualTo(0)
        );
    }

    @DisplayName("좋아요 숫자 테스트")
    @Test
    public void like_count_test() {
        // given
        CustomUser customUser1 = new CustomUser("id1", "test@gamil.com", "1234");
        CustomUser customUser2 = new CustomUser("id2", "test@gamil.com", "1234");
        CustomUser customUser3 = new CustomUser("id3", "test@gamil.com", "1234");
        Post post = new Post("temp_ip", "bar_id", 4.3f, "contents");

        // when
        post.like(customUser1);
        post.like(customUser2);
        post.like(customUser3);

        // then
        assertThat(post.likeCount()).isEqualTo(3);
    }

    @DisplayName("동일한 사용자가 같은 피드에 좋아요를 여러번 눌러도 한번만 카운트 된다")
    @Test
    public void user_can_have_only_one_like_count_test() {
        // given
        CustomUser customUser1 = new CustomUser("id1", "test@gamil.com", "1234");
        CustomUser customUser2 = new CustomUser("id2", "test@gamil.com", "1234");
        Post post = new Post("temp_ip", "bar_id", 4.3f, "contents");

        // when
        post.like(customUser1);
        post.like(customUser1);
        post.like(customUser1);
        post.like(customUser2);
        post.like(customUser2);

        // then
        assertAll(
                () -> assertThat(post.isLikeContain(customUser1.getId())).isTrue(),
                () -> assertThat(post.isLikeContain(customUser2.getId())).isTrue(),
                () -> assertThat(post.likeCount()).isEqualTo(2)
        );
    }
}
