package com.soolsul.soolsulserver.curation.presentation;

import com.soolsul.soolsulserver.bar.common.dto.response.BarSnackMenuResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.BarStreetNameAddressResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.BarOpeningHoursResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationDetailLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationPostLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationsLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.PostPhotoImageResponse;
import com.soolsul.soolsulserver.curation.facade.CurationQueryFacade;
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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    @DisplayName("큐레이션의 상세 내용을 조회한다")
    @Test
    void find_curation_details_by_curation_id() throws Exception {
        //given
        CurationDetailLookupResponse curationDetailLookupResponse = new CurationDetailLookupResponse(
                "curationTitle",
                "curationContent",
                "02-0000-0000",
                new BarStreetNameAddressResponse("", "서울", "중구", "을지로", 112, "11", "1층"),
                new BarOpeningHoursResponse(
                        LocalTime.of(17, 0, 0),
                        LocalTime.of(1, 0, 0)
                ),
                List.of(
                        new BarSnackMenuResponse("snackMenuName01", 10000),
                        new BarSnackMenuResponse("snackMenuName02", 20000)
                ),
                List.of(
                        new CurationPostLookupResponse(
                                "postTitle01",
                                "postContent",
                                List.of(
                                        new PostPhotoImageResponse("postImageUrl01"),
                                        new PostPhotoImageResponse("postImageUrl02"),
                                        new PostPhotoImageResponse("postImageUrl03")
                                ),
                                3
                        ),
                        new CurationPostLookupResponse(
                                "postTitle02",
                                "postContent",
                                List.of(
                                        new PostPhotoImageResponse("postImageUrl01"),
                                        new PostPhotoImageResponse("postImageUrl02"),
                                        new PostPhotoImageResponse("postImageUrl03")
                                ),
                                3
                        )
                )
        );
        given(curationQueryFacade.findCurationDetailsByCurationId(any()))
                .willReturn(curationDetailLookupResponse);


        //when
        ResultActions result = mockMvc.perform(get("/api/curations/{curationId}", "curationId")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpectAll(
                status().isOk(),
                jsonPath("$.code").value("C002"),
                jsonPath("$.message").isNotEmpty(),
                jsonPath("$.data").isNotEmpty()
        );

    }

}
