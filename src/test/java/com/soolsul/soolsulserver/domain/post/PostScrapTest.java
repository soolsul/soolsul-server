package com.soolsul.soolsulserver.domain.post;

import com.soolsul.soolsulserver.post.domain.PostScrap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatCode;

class PostScrapTest {

    @DisplayName("Post 생성 테스트")
    @Test
    void post_scrap_create_test() {
        assertThatCode(() -> new PostScrap("owner_id", "post_id"))
                .doesNotThrowAnyException();
    }

    @DisplayName("PostScrap ID가 같은 경우 equals를 통해 동일한 객체로 판단하는지 확인한다.")
    @Test
    void compare_equal_test() {
        PostScrap postScrapOne = createPostScrap("1", "owner_id", "post_id");
        PostScrap postScrapTwo = createPostScrap("1", "owner_id2", "post_id2");
        Assertions.assertThat(postScrapOne).isEqualTo(postScrapTwo);
    }

    @DisplayName("PostScrap Id가 다른 경우 equals를 통해 다른 객체로 판단한다.")
    @Test
    void compare_not_equal_test() {
        PostScrap postScrapOne = createPostScrap("1", "owner_id", "post_id");
        PostScrap postScrapTwo = createPostScrap("2", "owner_id", "post_id");
        Assertions.assertThat(postScrapOne).isNotEqualTo(postScrapTwo);
    }

    private PostScrap createPostScrap(String id, String ownerId, String postId) {
        PostScrap postScrap = new PostScrap(ownerId, postId);
        ReflectionTestUtils.setField(postScrap, "id", id);
        return postScrap;
    }
}
