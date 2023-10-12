package com.soolsul.soolsulserver.presentation.base;

import static org.mockito.Mockito.mock;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Bean;

import com.soolsul.soolsulserver.bar.facade.BarQueryFacade;
import com.soolsul.soolsulserver.curation.facade.CurationQueryFacade;

@TestConfiguration
public class PresentationTestConfig {

	@Bean
	public BarQueryFacade barQueryFacade() {
		return mock(BarQueryFacade.class, MockReset.withSettings(MockReset.AFTER));
	}

	@Bean
	public CurationQueryFacade curationQueryFacade() {
		return mock(CurationQueryFacade.class, MockReset.withSettings(MockReset.AFTER));
	}
}
