package com.soolsul.soolsulserver.presentation;

import com.soolsul.soolsulserver.bar.common.dto.response.FilteredBarsLookupResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static java.lang.String.valueOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BarQueryControllerTest extends ControllerTest {

    @DisplayName("지도상에 바 목록을 조회 요청에 관한 응답을 기다린다")
    @Test
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
