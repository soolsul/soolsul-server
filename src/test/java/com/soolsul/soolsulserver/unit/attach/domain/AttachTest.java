package com.soolsul.soolsulserver.unit.attach.domain;

import com.soolsul.soolsulserver.attach.domain.Attach;
import com.soolsul.soolsulserver.attach.domain.AttachRepository;
import com.soolsul.soolsulserver.bar.domain.BarMoodTag;
import com.soolsul.soolsulserver.bar.domain.MoodTagRepository;
import com.soolsul.soolsulserver.bar.domain.dto.BarMoodTagDto;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class AttachTest {

    private static final String RESTAURANT_ID = "restaurant_id";

    @Autowired
    AttachRepository attachRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MoodTagRepository moodTagRepository;

    @Autowired
    EntityManager em;

    Post post;
    BarMoodTag moodTag1;
    BarMoodTag moodTag2;

    @BeforeEach
    void setUp() {
        post = new Post("temp_ip", RESTAURANT_ID, 4.3f, "contents");
        moodTag1 = new BarMoodTag("uuid1", "mood_tag_1", true, 2);
        moodTag2 = new BarMoodTag("uuid2", "mood_tag_2", true, 1);
        postRepository.save(post);
        moodTagRepository.saveAll(List.of(moodTag1, moodTag2));
    }

    @DisplayName("Attach 중간 테이블을 통해 저장하고, Post와 관련된 테그를 모두 찾아온다.")
    @Test
    public void find_mood_tag_test() {
        // given
        Attach attach1 = new Attach(post.getId(), moodTag1.getId());
        Attach attach2 = new Attach(post.getId(), moodTag2.getId());
        attachRepository.saveAll(List.of(attach1, attach2));

        em.flush();
        em.clear();

        // when
        List<BarMoodTagDto> barMoodTags = moodTagRepository.findAllByPostId(post.getId());

        // then
        Assertions.assertThat(barMoodTags).extracting("barId").contains(moodTag1.getBarId(), moodTag2.getBarId());
        Assertions.assertThat(barMoodTags.size()).isEqualTo(2);
    }
}
