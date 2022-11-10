package com.soolsul.soolsulserver.unit.attach.domain;

import com.soolsul.soolsulserver.attach.domain.Attach;
import com.soolsul.soolsulserver.attach.domain.AttachRepository;
import com.soolsul.soolsulserver.bar.domain.AlcoholTagRepository;
import com.soolsul.soolsulserver.bar.domain.BarAlcoholTag;
import com.soolsul.soolsulserver.bar.domain.BarMoodTag;
import com.soolsul.soolsulserver.bar.domain.MoodTagRepository;
import com.soolsul.soolsulserver.bar.domain.dto.BarAlcoholTagDto;
import com.soolsul.soolsulserver.bar.domain.dto.BarMoodTagDto;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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
    AlcoholTagRepository alcoholTagRepository;

    @Autowired
    EntityManager em;

    Post post;
    BarMoodTag moodTag1;
    BarMoodTag moodTag2;
    BarAlcoholTag alcoholTag1;
    BarAlcoholTag alcoholTag2;

    @BeforeEach
    void setUp() {
        post = new Post("temp_ip", RESTAURANT_ID, 4.3f, "contents");
        postRepository.save(post);

        moodTag1 = new BarMoodTag("mood_tag_1", true, 2);
        moodTag2 = new BarMoodTag("mood_tag_2", true, 1);
        moodTagRepository.saveAll(List.of(moodTag1, moodTag2));

        alcoholTag1 = new BarAlcoholTag("한식");
        alcoholTag2 = new BarAlcoholTag("일식");
        alcoholTagRepository.saveAll(List.of(alcoholTag1, alcoholTag2));
    }

    @DisplayName("Attach 중간 테이블을 통해 저장하고, Post와 관련된 mood 테그를 모두 찾아온다.")
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
        Assertions.assertThat(barMoodTags).extracting("name").contains(moodTag1.getName(), moodTag2.getName());
        Assertions.assertThat(barMoodTags.size()).isEqualTo(2);
    }

    @Rollback(value = false)
    @DisplayName("Attach 중간 테이블을 통해 저장하고, Post와 관련된 alcohol 테그를 모두 찾아온다.")
    @Test
    public void find_alcohol_tag_test() {
        // given
        Attach attach1 = new Attach(post.getId(), alcoholTag1.getId());
        Attach attach2 = new Attach(post.getId(), alcoholTag2.getId());
        attachRepository.saveAll(List.of(attach1, attach2));

        em.flush();
        em.clear();

        // when
        List<BarAlcoholTagDto> barMoodTags = alcoholTagRepository.findAllByPostId(post.getId());

        // then
        Assertions.assertThat(barMoodTags).extracting("name").contains(alcoholTag1.getName(), alcoholTag2.getName());
        Assertions.assertThat(barMoodTags.size()).isEqualTo(2);
    }
}
