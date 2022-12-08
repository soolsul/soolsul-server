package com.soolsul.soolsulserver.bar.persistence;

import com.soolsul.soolsulserver.bar.common.dto.response.BarSnackMenuResponse;
import com.soolsul.soolsulserver.bar.domain.Bar;
import com.soolsul.soolsulserver.bar.domain.StreetNameAddress;
import com.soolsul.soolsulserver.common.config.QueryDslConfig;
import com.soolsul.soolsulserver.menu.snack.domain.Snack;
import com.soolsul.soolsulserver.menu.snack.domain.SnackMenu;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(QueryDslConfig.class)
@DataJpaTest(
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BarSnackMenuRepository.class)
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class BarSnackMenuRepositoryTest {

    @Autowired
    private BarSnackMenuRepository barSnackMenuRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("술집 안주 메뉴를 제공한다")
    @Test
    void find_all_bar_snack_menu_by_bar_id() {
        //given
        Bar savedBar = testEntityManager.persist(new Bar(
                "region01",
                "barCategory01",
                "술집01",
                "설명",
                "02-00000-0000",
                new StreetNameAddress("region", "city", "district", "roadName", 123, "11", "1층"),
                new Location(33, 120)
        ));

        Snack snack1 = testEntityManager.persist(new Snack("안주1", 1L));
        testEntityManager.persist(new SnackMenu(20_000L, snack1, savedBar.getId()));

        Snack snack2 = testEntityManager.persist(new Snack("안주2", 2L));
        testEntityManager.persist(new SnackMenu(20_000L, snack2, savedBar.getId()));

        //when
        List<BarSnackMenuResponse> barBestSnackMenus = barSnackMenuRepository.findAllBarSnackMenuByBarId(savedBar.getId());

        //then
        assertThat(barBestSnackMenus).hasSize(2);
    }

}
