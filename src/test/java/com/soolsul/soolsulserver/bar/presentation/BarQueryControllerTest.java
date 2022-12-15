package com.soolsul.soolsulserver.bar.presentation;

import com.soolsul.soolsulserver.bar.common.dto.response.FilteredBarsLookupResponse;
import com.soolsul.soolsulserver.bar.facade.BarQueryFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static java.lang.String.valueOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BarQueryController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)}, //security 설정을 종료하기 위한 설정
        excludeAutoConfiguration = SecurityAutoConfiguration.class //security auto configuration 을 종료하기 위한 설정
)
class BarQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarQueryFacade barQueryFacade;

    @DisplayName("지도상에 바 목록을 조회 요청에 관한 응답을 기다린다")
    @Test
//    @Disabled
    void find_bar_filtered_by_conditions() throws Exception {
        //given
        double latitude = 37.0;
        double longitude = 126.12;
        String barMoodTagNames = "";
        String barAlcoholTagNames = "";

        FilteredBarsLookupResponse filteredBarsLookupResponse = new FilteredBarsLookupResponse(Collections.EMPTY_LIST);
        given(barQueryFacade.findBarFilteredByConditions(any())).willReturn(filteredBarsLookupResponse);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/bars")
                .param("latitude", valueOf(latitude))
                .param("longitude", valueOf(longitude))
                .param("barMoodTagNames", barMoodTagNames)
                .param("barAlcoholTagNames", barAlcoholTagNames)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpectAll(
                status().isOk(),
                jsonPath("$.code").value("B001"),
                jsonPath("$.message").isNotEmpty()
        );
    }

}
