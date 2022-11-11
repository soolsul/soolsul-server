package com.soolsul.soolsulserver.unit.post.business;

import com.soolsul.soolsulserver.auth.User;
import com.soolsul.soolsulserver.bar.domain.BarRepository;
import com.soolsul.soolsulserver.post.business.PostQueryService;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostQueryServiceTest {

    @InjectMocks
    private PostQueryService postQueryService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private BarRepository barRepository;

    // TODO : 일단 auth쪽에 User를 정확하게 검증할수 없는 테스트
    @DisplayName("단건 Post 조회에 성공한다.")
    @Test
    public void find_detail_post_test() {
        // given
        User user = new User("user_uuid", "test@email.com", "1234", Collections.emptyList());
        List<PostPhoto> postPhotos = List.of(new PostPhoto("barId", "", "uuid1", ""), new PostPhoto("barId", "", "uuid2", ""));
        Post post = new Post("user_uuid", "", 4.3f, "content!");
        post.addPhotoList(postPhotos);

        given(postRepository.findById(anyString())).willReturn(Optional.of(post));

        // when
        PostDetailResponse response = postQueryService.findPostDetail("any_uuid");

        // then
        assertAll(
                () -> assertThat(response.getUserName()).isEqualTo("test"),
                () -> assertThat(response.getScore()).isEqualTo(4.3f),
                () -> assertThat(response.getImageUrls()).contains("uuid1", "uuid2"),
                () -> assertThat(response.getContents()).isEqualTo("content!")
        );
        verify(postRepository, times(1)).findById(any());

    }
}
