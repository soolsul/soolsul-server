package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.auth.User;
import com.soolsul.soolsulserver.auth.exception.UserNotFoundException;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import com.soolsul.soolsulserver.restaurant.domain.Restaurant;
import com.soolsul.soolsulserver.restaurant.domain.RestaurantRepository;
import com.soolsul.soolsulserver.restaurant.exception.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;

    public void create(User user, PostCreateRequest request) {
        if (invalidUserId(user)) {
            throw new UserNotFoundException();
        }

        Restaurant findRestaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(RestaurantNotFoundException::new);

        Post newPost = new Post(user.getId(),
                findRestaurant.getId(),
                request.getScore(),
                request.getPostContent());

        newPost.addPhotoList(convertPhotoList(request, findRestaurant));

        postRepository.save(newPost);
    }

    // TODO : 이미지 URL만 전달 받게 되는데, 이걸 PostPhoto로 저장하는 것이 맞는가?
    private List<PostPhoto> convertPhotoList(PostCreateRequest request, Restaurant findRestaurant) {
        return request.getImages()
                .stream()
                .map(url -> new PostPhoto(findRestaurant.getId(), "origin", "imageUrl", "."))
                .collect(Collectors.toList());
    }

    private boolean invalidUserId(User user) {
        return !StringUtils.hasText(user.getId());
    }
}
