package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.bar.common.dto.response.FilteredBarLookupResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.FilteredBarsLookupResponse;
import com.soolsul.soolsulserver.bar.facade.BarQueryFacade;
import com.soolsul.soolsulserver.bar.presentation.BarQueryController;
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
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
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
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BarQueryController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class BarDocumentation extends Documentation {

    @MockBean
    BarQueryFacade barQueryFacade;

    @DisplayName("????????? : Bar ?????? ??????")
    @WithMockUser
    @Test
    void find_post_list_success() throws Exception {
        FilteredBarLookupResponse responseOne = new FilteredBarLookupResponse("bar_id_1",
                "bar_name_1", "mood bar",
                37.49909732361135d, 126.9459247225816d,
                "alchol_tag", "alchol_tag_name",
                "mood_id", "mood_tag_name", LocalDateTime.now());

        FilteredBarLookupResponse responseTwo = new FilteredBarLookupResponse("bar_id_2",
                "bar_name_2", "mood bar 2",
                37.49909732361135d, 126.9459247225816d,
                "alchol_tag2", "alchol_tag_name2",
                "mood_id2", "mood_tag_name2", LocalDateTime.now());

        given(barQueryFacade.findBarFilteredByConditions(any())).willReturn(new FilteredBarsLookupResponse(List.of(responseOne, responseTwo)));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/bars")
                        .param("latitude", String.valueOf(37.565494))
                        .param("longitude", String.valueOf(126.992493))
                        .param("level", String.valueOf(7))
                        .param("barMoodTagNames", "????????????")
                        .param("barAlcoholTagNames", "??????")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("find-all-bar",
                                findAllBarRequestParam(),
                                findAllBarResponseBody())
                );
    }

    private Snippet findAllBarRequestParam() {
        return requestParameters(
                parameterWithName("longitude").description(Y_DESCRIPTION),
                parameterWithName("latitude").description(X_DESCRIPTION),
                parameterWithName("level").description(MAP_LEVEL_DESCRIPTION),
                parameterWithName("barMoodTagNames").description("?????? ?????????"),
                parameterWithName("barAlcoholTagNames").description("????????? ?????? ??? ??????")
        );
    }

    private Snippet findAllBarResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.barList[].barId").type(JsonFieldType.STRING).description("?????? ID"),
                fieldWithPath("data.barList[].barName").type(JsonFieldType.STRING).description("?????? ??????"),
                fieldWithPath("data.barList[].barDescription").type(JsonFieldType.STRING).description("?????? ??????"),
                fieldWithPath("data.barList[].latitude").type(JsonFieldType.NUMBER).description("?????? ??????"),
                fieldWithPath("data.barList[].longitude").type(JsonFieldType.NUMBER).description("?????? ??????"),
                fieldWithPath("data.barList[].alcoholTagId").type(JsonFieldType.STRING).description("????????? ??? ?????? ?????? ID"),
                fieldWithPath("data.barList[].alcoholTagName").type(JsonFieldType.STRING).description("????????? ??? ?????? ??????"),
                fieldWithPath("data.barList[].alcoholMoodId").type(JsonFieldType.STRING).description("????????? ????????? ?????? ID"),
                fieldWithPath("data.barList[].moodTagName").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????"),
                fieldWithPath("data.barList[].createdAt").type(JsonFieldType.STRING).description("?????? ?????????")
        );
    }
}
