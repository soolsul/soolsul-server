package com.soolsul.soolsulserver.curation.presentation;

import com.soolsul.soolsulserver.curation.dto.CurationsLookupResponse;
import com.soolsul.soolsulserver.curation.facade.CurationQueryFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CurationQueryController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)}, //security 설정을 종료하기 위한 설정
        excludeAutoConfiguration = SecurityAutoConfiguration.class //security auto configuration 을 종료하기 위한 설정
)
class CurationQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurationQueryFacade curationQueryFacade;

    @DisplayName("큐레이션 목록 조회 요청에 관한 응답을 확인한다")
    @Test
    void find_all_curations_by_location_range() throws Exception {
        //given
        double latitude = 37.0;
        double longitude = 126.12;

        given(curationQueryFacade.findAllCurationsByLocationRange(any()))
                .willReturn(new CurationsLookupResponse(new ArrayList<>()));

        //when
        ResultActions result = mockMvc.perform(get("/api/curations")
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude)));

        //then
        result.andExpectAll(
                status().isOk(),
                jsonPath("$.code").value("C001"),
                jsonPath("$.message").isNotEmpty(),
                jsonPath("$.data").isNotEmpty()
        );

    }

}
