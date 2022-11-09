package com.soolsul.soolsulserver.unit.post.business;

import com.soolsul.soolsulserver.auth.User;
import com.soolsul.soolsulserver.auth.exception.UserNotFoundException;
import com.soolsul.soolsulserver.post.business.PostCommandService;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import com.soolsul.soolsulserver.bar.domain.Restaurant;
import com.soolsul.soolsulserver.bar.domain.RestaurantRepository;
import com.soolsul.soolsulserver.bar.exception.RestaurantNotFoundException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostCommandServiceTest {

    private static final String RESTAURANT_ID = "restaurant_id";

    @InjectMocks
    private PostCommandService postCommandService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    private List<String> postPhotos;
    private User user;
    private PostCreateRequest request;

    @BeforeEach
    void setUp() {
        postPhotos = List.of("url1", "url2", "url3");
        user = new User("user_uuid", "test@email.com", "1234", Collections.emptyList());
        request = new PostCreateRequest("restaurant_id", "본문 내용", 4.3f, LocalDate.now(), postPhotos, null);
    }

    @DisplayName("가게가 존재할 경우, 정상적으로 게시물을 생성한다.")
    @Test
    public void create_post_test() {
        // given
        Restaurant restaurant = new Restaurant(RESTAURANT_ID, "region_id", "category_id", null);

        given(restaurantRepository.findById(anyString())).willReturn(Optional.of(restaurant));

        // when
        postCommandService.create(user.getId(), request);

        // then
        verify(postRepository, times(1)).save(any());
        verify(restaurantRepository, times(1)).findById(anyString());
    }

    @DisplayName("가게가 존재하지 않은 경우, 게시물 생성시 예외를 던진다.")
    @Test
    public void not_exists_restaurant_cant_create_post_test() {
        // given
        PostCreateRequest request = new PostCreateRequest("restaurant_id", "본문 내용", 4.3f, LocalDate.now(), postPhotos, null);

        given(restaurantRepository.findById(anyString())).willReturn(Optional.empty());

        // when
        ThrowableAssert.ThrowingCallable actual = () -> postCommandService.create(user.getId(), request);

        // then
        assertThatThrownBy(actual)
                .isInstanceOf(RestaurantNotFoundException.class)
                .hasMessage("해당 가게를 찾을 수 없습니다.");

        verify(postRepository, times(0)).save(any());
        verify(restaurantRepository, times(1)).findById(anyString());
    }

    @DisplayName("사용자의 id가 존재하는 경우에만 글을 생성할 수 있다.")
    @Test
    public void create_post_if_exists_user_test() {
        // given
        Restaurant restaurant = new Restaurant(RESTAURANT_ID, "region_id", "category_id", null);

        given(restaurantRepository.findById(anyString())).willReturn(Optional.of(restaurant));

        // when
        postCommandService.create(user.getId(), request);

        // then
        verify(postRepository, times(1)).save(any());
        verify(restaurantRepository, times(1)).findById(anyString());
    }

    @DisplayName("사용자의 id가 존재하지 않는 경우 예외를 던진다.")
    @Test
    public void throw_exception_if_not_exists_user_test() {
        // given
        User user = new User(null, "test@email.com", "1234", Collections.emptyList());

        // when
        ThrowableAssert.ThrowingCallable actual = () -> postCommandService.create(user.getId(), request);

        // then
        assertThatThrownBy(actual)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("해당 사용자를 찾을 수 없습니다.");

        verify(postRepository, times(0)).save(any());
        verify(restaurantRepository, times(0)).findById(anyString());
    }
}
