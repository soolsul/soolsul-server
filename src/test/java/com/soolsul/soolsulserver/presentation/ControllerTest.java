package com.soolsul.soolsulserver.presentation;

import com.soolsul.soolsulserver.bar.facade.BarQueryFacade;
import com.soolsul.soolsulserver.bar.presentation.BarQueryController;
import com.soolsul.soolsulserver.curation.facade.CurationQueryFacade;
import com.soolsul.soolsulserver.curation.presentation.CurationQueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {CurationQueryController.class, BarQueryController.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)}, //security 설정을 종료하기 위한 설정
        excludeAutoConfiguration = SecurityAutoConfiguration.class //security auto configuration 을 종료하기 위한 설정
)
abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected BarQueryFacade barQueryFacade;

    @MockBean
    protected CurationQueryFacade curationQueryFacade;

}
