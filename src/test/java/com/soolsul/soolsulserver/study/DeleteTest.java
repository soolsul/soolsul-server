package com.soolsul.soolsulserver.study;

import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class DeleteTest {

    private static final String BAR_ID = "1";

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager entityManager;

    @Transactional
    @Rollback(value = false)
    @Test
    public void delete_test() {
        // given
        Post post = new Post("ownerId", "barId", 4.2f, "contents");
        PostPhoto newPhoto1 = new PostPhoto(BAR_ID, "file1", "uuid1", ".jpg");
        PostPhoto newPhoto2 = new PostPhoto(BAR_ID, "file2", "uuid2", ".jpg");
        PostPhoto newPhoto3 = new PostPhoto(BAR_ID, "file3", "uuid3", ".jpg");
        post.addPhotoList(List.of(newPhoto1, newPhoto2, newPhoto3));

        postRepository.save(post);

        entityManager.flush();
        entityManager.clear();

        // when
        Post findPost = postRepository.findById(post.getId()).get();
        findPost.deletePhoto(newPhoto1);

        // then
        assertThat(findPost.getPhotos()).extracting("uuidFileUrl").contains("uuid2", "uuid3");
        assertThat(findPost.getPhotos().size()).isEqualTo(2);
    }
}
