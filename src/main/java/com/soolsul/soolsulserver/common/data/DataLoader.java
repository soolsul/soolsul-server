package com.soolsul.soolsulserver.common.data;

import com.soolsul.soolsulserver.bar.domain.Bar;
import com.soolsul.soolsulserver.bar.domain.BarRepository;
import com.soolsul.soolsulserver.bar.domain.StreetNameAddress;
import com.soolsul.soolsulserver.location.domain.LocationMagnificationLevel;
import com.soolsul.soolsulserver.location.persistence.LocationMagnificationLevelRepository;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.region.domain.Location;
import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.auth.presentation.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private static final String USER_EMAIL = "user@email.com";
    private static final String USER_PASSWORD = "password";
    private static final String NAME = "user";
    private static final String NICK_NAME = "shine";

    private final CustomUserDetailsService userDetailsService;
    private final LocationMagnificationLevelRepository locationMagnificationLevelRepositoryDsl;
    private final PostRepository postRepository;
    private final BarRepository barRepository;

    public static String postIdOne;
    public static String postIdTwo;
    public static String barId;

    public void loadData() {
        log.info("[call DataLoader]");
        userDetailsService.register(new UserRegisterRequest(USER_EMAIL, USER_PASSWORD, "02-123-4567", NAME, NICK_NAME));

        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(1, 60));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(2, 90));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(3, 150));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(4, 300));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(5, 750));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(6, 1500));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(7, 3000));

        Post post = new Post("test_owner_uuid", "bar_uuid", 4.3f, "contents");
        post.addPhoto(new PostPhoto("bar_id", "originName", "photo_url_1", ".jpg"));
        post.addPhoto(new PostPhoto("bar_id", "originName", "photo_url_2", ".jpg"));
        Post savedPost = postRepository.save(post);
        postIdOne = savedPost.getId();

        Post post2 = new Post("test_owner_uuid", "bar_uuid", 4.0f, "contents");
        post2.addPhoto(new PostPhoto("bar_id_2", "originName", "photo_url_3", ".jpg"));
        post2.addPhoto(new PostPhoto("bar_id_2", "originName", "photo_url_4", ".jpg"));
        Post savedPost2 = postRepository.save(post2);
        postIdTwo = savedPost2.getId();

        Bar saveBar = barRepository.save(
                new Bar(
                        "bar_uuid_1",
                        "region_id",
                        "bar_category_id",
                        "bar_name",
                        "02-0000-0000",
                        new StreetNameAddress("", "서울", "중구", "을지로", 18, "", "2층"),
                        new Location(37.49909732361135d, 126.9459247225816d)));
        barId = saveBar.getId();

        log.info("[init complete DataLoader]");
    }
}
