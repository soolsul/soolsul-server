package com.soolsul.soolsulserver.curation.persistence;

import com.soolsul.soolsulserver.bar.domain.Bar;
import com.soolsul.soolsulserver.bar.domain.BarAlcoholTag;
import com.soolsul.soolsulserver.bar.domain.BarMoodTag;
import com.soolsul.soolsulserver.bar.domain.StreetNameAddress;
import com.soolsul.soolsulserver.config.QueryDslConfig;
import com.soolsul.soolsulserver.curation.domain.Curation;
import com.soolsul.soolsulserver.curation.dto.CurationListLookupResponse;
import com.soolsul.soolsulserver.curation.dto.CurationLookupResponse;
import com.soolsul.soolsulserver.location.response.LocationSquareRangeCondition;
import com.soolsul.soolsulserver.region.domain.Location;
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
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CurationQueryRepository.class)
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class CurationQueryRepositoryTest {

    @Autowired
    private CurationQueryRepository curationQueryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void init() {
        initData();
    }

    @DisplayName("경위도와 요청 범위 내에 큐레이션 정보를 조회한다(술집, 무드 중복 큐레이션 반환)")
    @Test
    void find_all_curations_in_location_range() {
        //given
        LocationSquareRangeCondition locationSquareRangeCondition = new LocationSquareRangeCondition(119, 29, 122, 32);

        //when
        List<CurationListLookupResponse> curationListLookupResponses = curationQueryRepository.findAllCurationsInLocationRange(
                locationSquareRangeCondition
        );

        //then
        assertThat(curationListLookupResponses).hasSize(8);
    }

    private void initData() {
        //Bar data
        Bar bar01 = new Bar(
                "category1",
                "barName1",
                "description1",
                "description",
                "02-0000-0000",
                new StreetNameAddress("", "서울", "중구", "수표로", 12,  "12", ""),
                new Location(120, 30)
        );

        Bar bar02 = new Bar(
                "category2",
                "barName2",
                "description2",
                "description",
                "02-0000-0000",
                new StreetNameAddress("", "서울", "중구", "수표로", 12, "12" , ""),
                new Location(121, 31)
        );

        testEntityManager.persist(bar01);
        testEntityManager.persist(bar02);

        //Curation data
        Curation curation01 = new Curation("curationTitle1", "mainUrl1", "curationContent1", bar01.getId());
        Curation curation02 = new Curation("curationTitle2", "mainUrl2", "curationContent2", bar02.getId());

        testEntityManager.persist(curation01);
        testEntityManager.persist(curation02);

        //BarMoodTag data
        BarMoodTag barMoodTag01 = new BarMoodTag(bar01.getId(), "mood1", "moodName1", true, 0);
        BarMoodTag barMoodTag02 = new BarMoodTag(bar01.getId(), "mood2", "moodName2", true, 0);
        BarMoodTag barMoodTag03 = new BarMoodTag(bar02.getId(), "mood1", "moodName1", true, 0);
        BarMoodTag barMoodTag04 = new BarMoodTag(bar02.getId(), "mood2", "moodName2", true, 0);

        testEntityManager.persist(barMoodTag01);
        testEntityManager.persist(barMoodTag02);
        testEntityManager.persist(barMoodTag03);
        testEntityManager.persist(barMoodTag04);

        //BarAlcoholTag data
        BarAlcoholTag barAlcoholTag01 = new BarAlcoholTag(bar01.getId(), "alcoholCategoryId1", "alcoholCategoryName1");
        BarAlcoholTag barAlcoholTag02 = new BarAlcoholTag(bar01.getId(), "alcoholCategoryId2", "alcoholCategoryName2");
        BarAlcoholTag barAlcoholTag03 = new BarAlcoholTag(bar02.getId(), "alcoholCategoryId1", "alcoholCategoryName1");
        BarAlcoholTag barAlcoholTag04 = new BarAlcoholTag(bar02.getId(), "alcoholCategoryId2", "alcoholCategoryName2");

        testEntityManager.persist(barAlcoholTag01);
        testEntityManager.persist(barAlcoholTag02);
        testEntityManager.persist(barAlcoholTag03);
        testEntityManager.persist(barAlcoholTag04);
    }

    @DisplayName("큐레이션을 조회한다")
    @Test
    void findById() {
        //given
        Curation savedCuration = testEntityManager.persist(new Curation("curationTitle1", "mainUrl1", "curationContent1", "bar01"));

        //when
        CurationLookupResponse curationLookupResponse = curationQueryRepository.findById(savedCuration.getId());

        //then
        assertAll(
                () -> assertThat(savedCuration.getTitle()).isEqualTo(curationLookupResponse.curationTitle()),
                () -> assertThat(savedCuration.getContent()).isEqualTo(curationLookupResponse.curationContent()),
                () -> assertThat(savedCuration.getMainPictureUrl()).isEqualTo(curationLookupResponse.mainPictureUrl())
        );

    }

}
