package com.soolsul.soolsulserver.persistence;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.soolsul.soolsulserver.bar.domain.BarAlcoholTag;
import com.soolsul.soolsulserver.bar.persistence.BarAlcoholTagQueryRepository;
import com.soolsul.soolsulserver.persistence.base.PersistenceTest;

@PersistenceTest
class BarAlcoholTagQueryRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BarAlcoholTagQueryRepository barAlcoholTagQueryRepository;

    @DisplayName("알콜 카테고리 아이디를 통해 바-술종류 태그를 조회한다")
    @Test
    void find_bar_alcohol_tag_ids_by_alcohol_category_ids() {
        testEntityManager.persist(new BarAlcoholTag("bar01", "alcoholCategory01", "alcoholCategoryName01"));
        testEntityManager.persist(new BarAlcoholTag("bar02", "alcoholCategory01", "alcoholCategoryName01"));
        testEntityManager.persist(new BarAlcoholTag("bar03", "alcoholCategory01", "alcoholCategoryName01"));
        testEntityManager.persist(new BarAlcoholTag("bar04", "alcoholCategory02", "alcoholCategoryName02"));
        testEntityManager.persist(new BarAlcoholTag("bar05", "alcoholCategory02", "alcoholCategoryName02"));
        testEntityManager.persist(new BarAlcoholTag("bar06", "alcoholCategory03", "alcoholCategoryName02"));

        List<String> alcoholCategoryIds = List.of("alcoholCategory01", "alcoholCategory02");

        //when
        List<String> barAlcoholTagIds = barAlcoholTagQueryRepository.findBarAlcoholTagIdsByAlcoholCategoryIds(alcoholCategoryIds);

        //then
        Assertions.assertThat(barAlcoholTagIds).hasSize(5);
    }

}
