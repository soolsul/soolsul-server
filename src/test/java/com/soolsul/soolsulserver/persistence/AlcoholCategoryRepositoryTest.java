package com.soolsul.soolsulserver.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.soolsul.soolsulserver.menu.alcohol.domain.AlcoholCategory;
import com.soolsul.soolsulserver.menu.alcohol.persistence.AlcoholCategoryRepository;
import com.soolsul.soolsulserver.persistence.base.PersistenceTest;

@PersistenceTest
class AlcoholCategoryRepositoryTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	protected AlcoholCategoryRepository alcoholCategoryRepository;

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
