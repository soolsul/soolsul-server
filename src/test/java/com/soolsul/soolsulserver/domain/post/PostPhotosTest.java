package com.soolsul.soolsulserver.domain.post;

import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.exception.MinimumPhotoCountException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PostPhotosTest {

    private static final String BAR_ID = "1";
    private PostPhoto newPhoto1;
    private PostPhoto newPhoto2;
    private PostPhoto newPhoto3;
    private Post post;

    @BeforeEach
    void setUp() {
        newPhoto1 = new PostPhoto(BAR_ID, "file1", "uuid1", ".jpg");
        newPhoto2 = new PostPhoto(BAR_ID, "file2", "uuid2", ".jpg");
        newPhoto3 = new PostPhoto(BAR_ID, "file3", "uuid3", ".jpg");
        post = new Post("temp_ip", "bar_id", 4.3f, "contents");
    }

    @DisplayName("Photos에 Photo한장을 추가한다.")
    @Test
    void add_post_photo_test() {
        // when
        post.addPhoto(newPhoto1);
        post.addPhoto(newPhoto2);
        post.addPhoto(newPhoto3);

        // then
        assertThat(post.getPhotos()).containsExactly(newPhoto1, newPhoto2, newPhoto3);
    }

    @DisplayName("Photos일급컬랙션을 리스트로 초기화 할 수 있다.")
    @Test
    void add_post_photo_list_test() {
        // given
        List<PostPhoto> photos = List.of(this.newPhoto1, newPhoto2, newPhoto3);

        // when
        post.addPhotoList(photos);

        // then
        assertThat(post.getPhotos()).containsExactly(newPhoto1, newPhoto2, newPhoto3);
    }

    @DisplayName("Phtots일급컬랙션에서 단건 사진을 삭제할 수 있다.")
    @Test
    void delete_post_photo_test() {
        // given
        List<PostPhoto> photos = List.of(newPhoto1, newPhoto2, newPhoto3);
        post.addPhotoList(photos);

        // when
        post.deletePhoto(newPhoto1);

        // then
        assertThat(post.getPhotos()).containsExactly(newPhoto2, newPhoto3);
    }

    @DisplayName("Phtots일급컬랙션에는 최소 한장의 사진은 남아야 한다.")
    @Test
    void cant_delete_last_photo_test() {
        // given
        List<PostPhoto> photos = List.of(newPhoto1);
        post.addPhotoList(photos);

        // when
        ThrowableAssert.ThrowingCallable actual = () -> post.deletePhoto(newPhoto1);

        // then
        assertThatThrownBy(actual)
                .isInstanceOf(MinimumPhotoCountException.class)
                .hasMessage("사진은 최소 한장 이상이 있어야 합니다.");
    }

    @DisplayName("Photos일급컬랙션을 초기화 할 수 있다.")
    @Test
    void clear_photo_test() {
        // given
        List<PostPhoto> photos = List.of(newPhoto1, newPhoto2, newPhoto3);
        post.addPhotoList(photos);

        // when
        post.clearAllPhotos();

        // then
        assertThat(post.getPhotos().size()).isEqualTo(0);
    }
}
