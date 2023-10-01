package com.soolsul.soolsulserver.domain.post;

import com.soolsul.soolsulserver.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class PostTest {

    @DisplayName("Post 생성 테스트")
    @Test
    void post_create_test() {
        assertThatCode(() -> new Post("temp_ip", "bar_id", 4.3f, "contents"))
                .doesNotThrowAnyException();
    }

    @DisplayName("Post의 ID가 같은 경우 equals를 통해 동일한 객체로 판단하는지 확인한다.")
    @Test
    void compare_equal_test() {
        Post ownerOne = createPost("1", "owner_one");
        Post ownerTwo = createPost("1", "owner_two");
        assertThat(ownerOne).isEqualTo(ownerTwo);
    }

    @DisplayName("Post Id가 다른 경우 equals를 통해 다른 객체로 판단한다.")
    @Test
    void compare_not_equal_test() {
        Post ownerOne = createPost("1", "owner_one");
        Post ownerTwo = createPost("2", "owner_onw");
        assertThat(ownerOne).isNotEqualTo(ownerTwo);
    }

    private Post createPost(String id, String ownerId) {
        Post post = new Post(ownerId, "bar_id", 3.3f, "contents");
        ReflectionTestUtils.setField(post, "id", id);
        return post;
    }
}
