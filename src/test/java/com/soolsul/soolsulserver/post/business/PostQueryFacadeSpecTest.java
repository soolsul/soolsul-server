package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.user.auth.CustomUser;
import com.soolsul.soolsulserver.user.auth.repository.dto.response.UserLookUpResponse;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupResponse;
import com.soolsul.soolsulserver.post.business.dto.request.PostLookupRequest;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import com.soolsul.soolsulserver.user.auth.CustomUser;
import com.soolsul.soolsulserver.user.auth.repository.dto.UserLookUpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
public class PostQueryFacadeSpecTest {

    @InjectMocks
    private PostQueryService postQueryService;

    @Mock
    private BarQueryRepository barQueryRepository;

    @DisplayName("단건 Post 조회에 성공한다.")
    @Test
    public void find_detail_post_test() {
        // given
        CustomUser customUser = new CustomUser("id1", "test@email.com", "1234");
        UserLookUpResponse userLookUpResponse = new UserLookUpResponse(
                "id1",
                "test@email.com",
                "1234",
                "010-1111-1212",
                "test",
                "shine",
                "url"
        );
        List<PostPhoto> postPhotos = List.of(new PostPhoto("barId", "", "uuid1", ""), new PostPhoto("barId", "", "uuid2", ""));
        BarLookupResponse barLookupResponse = new BarLookupResponse(
                "barId",
                "region_id",
                "category_id",
                "bar_name",
                "description",
                "02-0000-0000",
                null,
                null,
                null
        );
        Post post = new Post("user_uuid", "", 4.3f, "content!");
        post.addPhotoList(postPhotos);
        post.like(customUser);

        given(barQueryRepository.findById(anyString())).willReturn(Optional.of(barLookupResponse));

        // when
        PostDetailResponse response = postQueryService.findPostDetail(new PostLookupRequest(customUser.getId(), "any_uuid", post, userLookUpResponse));

        // then
        assertAll(
                () -> assertThat(response.score()).isEqualTo(4.3f),
                () -> assertThat(response.contents()).isEqualTo("content!"),
                () -> assertThat(response.imageUrls()).contains("uuid1", "uuid2"),
                () -> assertThat(response.like().count()).isEqualTo(1),
                () -> assertThat(response.like().userLikeStatus()).isTrue()
        );
        verify(barQueryRepository, times(1)).findById(any());
    }
}
