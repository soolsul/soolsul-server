package com.soolsul.soolsulserver.menu.persistence;

import com.soolsul.soolsulserver.config.QueryDslConfig;
import com.soolsul.soolsulserver.menu.alcohol.domain.AlcoholCategory;
import com.soolsul.soolsulserver.menu.alcohol.persistence.AlcoholCategoryRepository;
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
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AlcoholCategoryRepository.class)
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class AlcoholCategoryRepositoryTest {

    @Autowired
    private AlcoholCategoryRepository alcoholCategoryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("술 카테고리명을 통해 술 카테고리를 조회한다")
    @Test
    void find_alcohol_category_ids_by_alcohol_category_names() {
        //given
        AlcoholCategory alcoholCategory01 = testEntityManager.persist(new AlcoholCategory("alcoholCategory01"));
        AlcoholCategory alcoholCategory02 = testEntityManager.persist(new AlcoholCategory("alcoholCategory02"));
        AlcoholCategory alcoholCategory03 = testEntityManager.persist(new AlcoholCategory("alcoholCategory03"));

        List<String> alcoholCategoryNames = List.of(alcoholCategory01.getName(), alcoholCategory02.getName());

        //when
        List<String> alcoholCategoryIds
                = alcoholCategoryRepository.findAlcoholCategoryIdsByAlcoholCategoryNames(alcoholCategoryNames);

        //then
        assertThat(alcoholCategoryIds).hasSize(2);
    }

}
