package com.soolsul.soolsulserver.post.domain.query;

import com.soolsul.soolsulserver.config.QueryDslConfig;
import com.soolsul.soolsulserver.curation.dto.CurationPostLookupResponse;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.user.auth.CustomUser;
import com.soolsul.soolsulserver.user.auth.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(QueryDslConfig.class)
@DataJpaTest(
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = PostQueryRepositoryImpl.class)
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PostQueryRepositoryImplTest {

    @Autowired
    private PostQueryRepositoryImpl postQueryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void init() {
        initData();
    }

    @DisplayName("피드에 목록을 조회한다")
    @Test
    void find_all_post_by_bar_id() {
        //given
        String barId = "bar01";

        //when
        List<CurationPostLookupResponse> curationPostLookupResponses = postQueryRepository.findAllPostByBarId(barId);

        //then
        assertAll(
                () -> assertThat(curationPostLookupResponses).hasSize(1),
                () -> assertThat(curationPostLookupResponses.get(0).getPostImageUrls()).hasSize(4),
                () -> assertThat(curationPostLookupResponses.get(0).getUserLikes()).isEqualTo(2)
        );

        System.out.println(curationPostLookupResponses);
    }

    private void initData() {
        CustomUser customUser1 = new CustomUser( "soolsul1@gmail.com", "abcd1234");
        CustomUser customUser2 = new CustomUser("soolsul2@gmail.com", "abcd1234");

        testEntityManager.persist(customUser1);
        testEntityManager.persist(customUser2);

        UserInfo userInfo1 = new UserInfo(customUser1.getId(), "010-0000-0000", "nickname01", "name01");
        UserInfo userInfo2 = new UserInfo(customUser2.getId(), "010-0000-0000", "nickname02", "name02");

        testEntityManager.persist(userInfo1);
        testEntityManager.persist(userInfo2);

        Post post1 = new Post(customUser1.getId(), "bar01", 3.3f, "content1");
        Post post2 = new Post(customUser1.getId(), "bar02", 3.4f, "content2");

        PostPhoto postPhoto1 = new PostPhoto("bar01", "filename01", "uuidFileUrl1", ".jpg");
        PostPhoto postPhoto2 = new PostPhoto("bar01", "filename02", "uuidFileUrl2", ".jpg");
        PostPhoto postPhoto3 = new PostPhoto("bar01", "filename03", "uuidFileUrl3", ".jpg");
        PostPhoto postPhoto4 = new PostPhoto("bar01", "filename04", "uuidFileUrl4", ".jpg");
        PostPhoto postPhoto5 = new PostPhoto("bar02", "filename05", "uuidFileUrl5", ".jpg");

        post1.addPhoto(postPhoto1);
        post1.addPhoto(postPhoto2);
        post1.addPhoto(postPhoto3);
        post1.addPhoto(postPhoto4);
        post2.addPhoto(postPhoto5);

        testEntityManager.persist(post1);
        testEntityManager.persist(post2);

        post1.like(customUser1);
        post1.like(customUser2);

    }

}
