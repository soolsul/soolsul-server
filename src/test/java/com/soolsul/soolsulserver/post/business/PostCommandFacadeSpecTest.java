package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.bar.exception.BarNotFoundException;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
import com.soolsul.soolsulserver.bar.common.dto.response.BarLookupResponse;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.common.dto.request.PostCreateRequest;
import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import com.soolsul.soolsulserver.user.auth.exception.UserNotFoundException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostCommandFacadeSpecTest {

    private static final String BAR_ID = "bar_id";

    @InjectMocks
    private PostCommandService postCommandService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private BarQueryRepository barQueryRepository;

    private List<String> postPhotos;
    private CustomUser customUser;
    private PostCreateRequest request;

    @BeforeEach
    void setUp() {
        postPhotos = List.of("url1", "url2", "url3");
        customUser = new CustomUser("1", "test@email.com", "1234");
        request = new PostCreateRequest("bar_id", "본문 내용", 4.3f, LocalDate.now(), postPhotos, null);
    }

    @DisplayName("가게가 존재할 경우, 정상적으로 게시물을 생성한다.")
    @Test
    void create_post_test() {
        // given
        BarLookupResponse barLookupResponse = new BarLookupResponse(
                BAR_ID,
                "region_id",
                "category_id",
                "bar_name",
                "description",
                "02-0000-0000",
                null,
                null,
                null
        );

        given(barQueryRepository.findById(anyString())).willReturn(Optional.of(barLookupResponse));

        // when
        postCommandService.create(customUser.getId(), request);

        // then
        verify(postRepository, times(1)).save(any());
        verify(barQueryRepository, times(1)).findById(anyString());
    }


    @DisplayName("가게가 존재하지 않은 경우, 게시물 생성시 예외를 던진다.")
    @Test
    void not_exists_bar_cant_create_post_test() {
        // given
        PostCreateRequest request = new PostCreateRequest("bar_id", "본문 내용", 4.3f, LocalDate.now(), postPhotos, null);

        given(barQueryRepository.findById(anyString())).willReturn(Optional.empty());

        // when
        ThrowableAssert.ThrowingCallable actual = () -> postCommandService.create(customUser.getId(), request);

        // then
        assertThatThrownBy(actual)
                .isInstanceOf(BarNotFoundException.class)
                .hasMessage("해당 술집을 찾을 수 없습니다.");

        verify(postRepository, times(0)).save(any());
        verify(barQueryRepository, times(1)).findById(anyString());
    }

    @DisplayName("사용자의 id가 존재하는 경우에만 글을 생성할 수 있다.")
    @Test
    void create_post_if_exists_user_test() {
        // given
        BarLookupResponse barLookupResponse = new BarLookupResponse(
                BAR_ID,
                "region_id",
                "category_id",
                "bar_name",
                "description",
                "02-0000-0000",
                null,
                null,
                null
        );

        given(barQueryRepository.findById(anyString())).willReturn(Optional.of(barLookupResponse));

        // when
        postCommandService.create(customUser.getId(), request);

        // then
        verify(postRepository, times(1)).save(any());
        verify(barQueryRepository, times(1)).findById(anyString());
    }

    @DisplayName("사용자의 id가 존재하지 않는 경우 예외를 던진다.")
    @Test
    void throw_exception_if_not_exists_user_test() {
        // given
        CustomUser noEmailCustomUser = new CustomUser(null, "1234");

        // when
        ThrowableAssert.ThrowingCallable actual = () -> postCommandService.create(noEmailCustomUser.getId(), request);

        // then
        assertThatThrownBy(actual)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("해당 사용자를 찾을 수 없습니다.");

        verify(postRepository, times(0)).save(any());
        verify(barQueryRepository, times(0)).findById(anyString());
    }
}
