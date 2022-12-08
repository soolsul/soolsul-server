package com.soolsul.soolsulserver.bar.persistence;

import com.soolsul.soolsulserver.bar.domain.BarMoodTag;
import com.soolsul.soolsulserver.bar.domain.MoodTag;
import com.soolsul.soolsulserver.common.config.QueryDslConfig;
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

@Import(QueryDslConfig.class)
@DataJpaTest(
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BarMoodTagRepository.class)
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class BarMoodTagRepositoryTest {

    @Autowired
    private BarMoodTagRepository barMoodTagRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findBarMoodTagIdsByMoodNames() {
        //given
        MoodTag mood01 = testEntityManager.persist(new MoodTag("가성비좋은"));
        MoodTag mood02 = testEntityManager.persist(new MoodTag("분위기좋은"));
        MoodTag mood03 = testEntityManager.persist(new MoodTag("푸짐한"));
        MoodTag mood04 = testEntityManager.persist(new MoodTag("격식있는"));
//        MoodTag mood05 = testEntityManager.persist(new MoodTag("고급스러운"));
//        MoodTag mood06 = testEntityManager.persist(new MoodTag("서민적인"));
//        MoodTag mood07 = testEntityManager.persist(new MoodTag("시끌벅적한"));
//        MoodTag mood08 = testEntityManager.persist(new MoodTag("조용한"));
//        MoodTag mood09 = testEntityManager.persist(new MoodTag("깔끔한"));
//        MoodTag mood10 = testEntityManager.persist(new MoodTag("이색적인"));
//        MoodTag mood11 = testEntityManager.persist(new MoodTag("뷰가좋은"));
//        MoodTag mood12 = testEntityManager.persist(new MoodTag("예쁜"));

        testEntityManager.persist(new BarMoodTag("1", mood01.getId(), mood01.getName(), false, 0));
        testEntityManager.persist(new BarMoodTag("1", mood02.getId(), mood02.getName(), false, 0));
        testEntityManager.persist(new BarMoodTag("1", mood03.getId(), mood03.getName(), false, 0));
        testEntityManager.persist(new BarMoodTag("2", mood01.getId(), mood01.getName(), false, 0));
        testEntityManager.persist(new BarMoodTag("2", mood02.getId(), mood02.getName(), false, 0));
        testEntityManager.persist(new BarMoodTag("2", mood04.getId(), mood04.getName(), false, 0));

        System.out.println(mood01.getName());
        System.out.println(mood02.getName());
        System.out.println(mood03.getName());

        //when
        List<String> barMoodTagIds = barMoodTagRepository.findBarMoodTagIdsByMoodNames(List.of(mood01.getName(), mood02.getName(), mood03.getName()));

        //then
        assertThat(barMoodTagIds).hasSize(5);
    }
}
