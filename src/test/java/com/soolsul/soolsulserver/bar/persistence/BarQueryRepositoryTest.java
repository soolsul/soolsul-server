package com.soolsul.soolsulserver.bar.persistence;

import com.soolsul.soolsulserver.bar.domain.Bar;
import com.soolsul.soolsulserver.bar.domain.StreetNameAddress;
import com.soolsul.soolsulserver.bar.exception.BarNotFoundException;
import com.soolsul.soolsulserver.bar.common.dto.response.BarLookupResponse;
import com.soolsul.soolsulserver.common.config.QueryDslConfig;
import com.soolsul.soolsulserver.region.domain.Location;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(QueryDslConfig.class)
@DataJpaTest(
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BarQueryRepository.class)
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class BarQueryRepositoryTest {

    @Autowired
    private BarQueryRepository barQueryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("알콜 카테고리 아이디를 통해 바-술종류 태그를 조회한다")
    @Test
    void find_by_id() {
        Bar bar = testEntityManager.persist(
                new Bar(
                        "regionId01",
                        "barCategoryId",
                        "barName",
                        "description",
                        "02-0000-0000",
                        new StreetNameAddress("", "서울", "중구", "을지로", 18, "", "2층"),
                        new Location(50.0, 50.0))
        );

        BarLookupResponse findBar = barQueryRepository.findById(bar.getId()).orElseThrow(BarNotFoundException::new);

        assertAll(
                () -> assertThat(bar.getId()).isEqualTo(findBar.id()),
                () -> assertThat(bar.getDescription()).isEqualTo(findBar.description()),
                () -> assertThat(bar.getBarCategoryId()).isEqualTo(findBar.barCategoryId())

        );
    }

}
