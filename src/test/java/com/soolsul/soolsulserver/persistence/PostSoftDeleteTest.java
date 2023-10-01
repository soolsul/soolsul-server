package com.soolsul.soolsulserver.persistence;

import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostSoftDeleteTest extends RepositoryTest {

    @Test
    void delete_test() {
        // given
        Post post = new Post("ownerId", "barId", 4.2f, "contents");
        PostPhoto newPhoto1 = new PostPhoto("1", "file1", "uuid1", ".jpg");
        PostPhoto newPhoto2 = new PostPhoto("1", "file2", "uuid2", ".jpg");
        PostPhoto newPhoto3 = new PostPhoto("1", "file3", "uuid3", ".jpg");
        post.addPhotoList(List.of(newPhoto1, newPhoto2, newPhoto3));

        postRepository.save(post);

        testEntityManager.flush();
        testEntityManager.clear();

        // when
        Post findPost = postRepository.findById(post.getId()).get();
        findPost.deletePhoto(newPhoto1);

        // then
        assertThat(findPost.getPhotos()).extracting("uuidFileUrl").contains("uuid2", "uuid3");
        assertThat(findPost.getPhotos().size()).isEqualTo(2);
    }

}
