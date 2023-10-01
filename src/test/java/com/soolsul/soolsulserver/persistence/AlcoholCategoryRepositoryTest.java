package com.soolsul.soolsulserver.persistence;

import com.soolsul.soolsulserver.menu.alcohol.domain.AlcoholCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AlcoholCategoryRepositoryTest extends RepositoryTest {

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
