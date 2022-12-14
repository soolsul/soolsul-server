package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.bar.common.dto.response.BarSnackMenuResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.BarStreetNameAddressResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.BarOpeningHoursResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationDetailLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationListLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationPostLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationsLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.PostPhotoImageResponse;
import com.soolsul.soolsulserver.curation.facade.CurationQueryFacade;
import com.soolsul.soolsulserver.curation.presentation.CurationQueryController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.spec.internal.MediaTypes;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

import java.time.LocalTime;
import java.util.List;

import static com.soolsul.soolsulserver.documentation.Constants.MAP_LEVEL_DESCRIPTION;
import static com.soolsul.soolsulserver.documentation.Constants.X_DESCRIPTION;
import static com.soolsul.soolsulserver.documentation.Constants.Y_DESCRIPTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CurationQueryController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CurationDocumentation extends Documentation {

    @MockBean
    CurationQueryFacade curationQueryFacade;

    @DisplayName("????????? : Curation ?????? ??????")
    @Test
    void find_curation_list_success() throws Exception {
        CurationListLookupResponse curationResponseOne = new CurationListLookupResponse("curation_id_1", "url", "title_1", "content_1", "????????????1", "??????1");
        CurationListLookupResponse curationResponseTwo = new CurationListLookupResponse("curation_id_2", "url", "title_2", "content_2", "????????????2", "??????2");
        CurationsLookupResponse curationsLookupResponse = new CurationsLookupResponse(List.of(curationResponseOne, curationResponseTwo));

        given(curationQueryFacade.findAllCurationsByLocationRange(any())).willReturn(curationsLookupResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/curations")
                        .param("latitude", String.valueOf(37.565494))
                        .param("longitude", String.valueOf(126.992493))
                        .param("level", String.valueOf(7))
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("find-all-curation",
                                findAllCurationRequestParam(),
                                findAllCurationResponseBody())
                );
    }

    @DisplayName("????????? : Curation ?????? ??????")
    @Test
    void find_specific_curation_success() throws Exception {
        BarSnackMenuResponse snackMenuResponse = new BarSnackMenuResponse("????????????", 15000L);
        CurationPostLookupResponse curationResponse = new CurationPostLookupResponse("contents", "?????????1", List.of(new PostPhotoImageResponse("url1"), new PostPhotoImageResponse("url2")), 12);

        LocalTime start = LocalTime.of(16, 00, 00);
        LocalTime end = LocalTime.of(23, 59, 00);
        CurationDetailLookupResponse curationDetailLookupResponse = new CurationDetailLookupResponse("curation_title", "contents", "010-1234-5678",
                new BarStreetNameAddressResponse("region_name", "seoul", "?????????", "?????????", 133, "road_detail", "location_detail"),
                new BarOpeningHoursResponse(start, end),
                List.of(snackMenuResponse),
                List.of(curationResponse)
        );

        given(curationQueryFacade.findCurationDetailsByCurationId(any())).willReturn(curationDetailLookupResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/curations/{curationId}", "curation_uuid")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("find-specific-curation",
                                findSpecificCurationRequestParam(),
                                findSpecificCurationResponseBody())
                );
    }

    private Snippet findSpecificCurationRequestParam() {
        return pathParameters(
                parameterWithName("curationId").description("???????????? ID")
        );
    }

    private Snippet findSpecificCurationResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.curationTitle").type(JsonFieldType.STRING).description("???????????? ID"),
                fieldWithPath("data.curationContent").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("?????? ?????????"),
                fieldWithPath("data.barStreetNameAddressResponse.simpleStreetNameAddress").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                fieldWithPath("data.barStreetNameAddressResponse.detailStreetNameAddress").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                fieldWithPath("data.barOpeningHoursResponse.openTime").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                fieldWithPath("data.barOpeningHoursResponse.closeTime").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                fieldWithPath("data.barSnackMenuResponses[].snackMenuName").type(JsonFieldType.STRING).description("?????? ??????"),
                fieldWithPath("data.barSnackMenuResponses[].cost").type(JsonFieldType.NUMBER).description("?????? ??????"),
                fieldWithPath("data.curationPostLookupResponses[].content").type(JsonFieldType.STRING).description("??????"),
                fieldWithPath("data.curationPostLookupResponses[].userName").type(JsonFieldType.STRING).description("????????? ??????"),
                fieldWithPath("data.curationPostLookupResponses[].postImageUrls[].postPhotoImageUrl").type(JsonFieldType.STRING).description("?????? url"),
                fieldWithPath("data.curationPostLookupResponses[].userLikes").type(JsonFieldType.NUMBER).description("????????? ???")
        );
    }

    private Snippet findAllCurationRequestParam() {
        return requestParameters(
                parameterWithName("longitude").description(Y_DESCRIPTION),
                parameterWithName("latitude").description(X_DESCRIPTION),
                parameterWithName("level").description(MAP_LEVEL_DESCRIPTION)
        );
    }

    private Snippet findAllCurationResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.curationList[].curationId").type(JsonFieldType.STRING).description("???????????? ID"),
                fieldWithPath("data.curationList[].mainPictureUrl").type(JsonFieldType.STRING).description("?????? ?????? url"),
                fieldWithPath("data.curationList[].title").type(JsonFieldType.STRING).description("???????????? ??????"),
                fieldWithPath("data.curationList[].content").type(JsonFieldType.STRING).description("???????????? ??????"),
                fieldWithPath("data.curationList[].barMoodTagName").type(JsonFieldType.STRING).description("??????????????? ?????? ????????? ??????"),
                fieldWithPath("data.curationList[].barAlcoholTagName").type(JsonFieldType.STRING).description("??????????????? ?????? ??? ?????? ??????")
        );
    }
}
