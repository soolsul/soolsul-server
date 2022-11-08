package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.auth.User;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.auth.exception.UserNotFoundException;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import com.soolsul.soolsulserver.restaurant.domain.Restaurant;
import com.soolsul.soolsulserver.restaurant.domain.RestaurantRepository;
import com.soolsul.soolsulserver.restaurant.exception.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;

    public void create(User user, PostCreateRequest request) {
        if(invalidUserId(user)) {
            throw new UserNotFoundException();
        }

        Restaurant findRestaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(RestaurantNotFoundException::new);

        Post newPost = Post.of(user.getId(), findRestaurant.getId(), request);
        postRepository.save(newPost);
    }

    private boolean invalidUserId(User user) {
        return !StringUtils.hasText(user.getId());
    }
}
