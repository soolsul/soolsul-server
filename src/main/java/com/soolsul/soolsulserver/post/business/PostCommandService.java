package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.auth.User;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import com.soolsul.soolsulserver.restaurant.Restaurant;
import com.soolsul.soolsulserver.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;

    public void create(User user, PostCreateRequest request) {
        Restaurant findRestaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(RuntimeException::new);

        Post newPost = Post.of(user.getId(), findRestaurant.getId(), request);
        postRepository.save(newPost);
    }
}
