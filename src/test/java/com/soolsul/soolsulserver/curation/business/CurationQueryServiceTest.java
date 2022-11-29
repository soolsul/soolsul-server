package com.soolsul.soolsulserver.curation.business;

import com.soolsul.soolsulserver.curation.dto.CurationListLookupResponse;
import com.soolsul.soolsulserver.curation.persistence.CurationQueryRepository;
import com.soolsul.soolsulserver.location.response.LocationSquareRangeCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CurationQueryServiceTest {

    private CurationQueryService curationQueryService;

    @Mock
    private CurationQueryRepository curationQueryRepository;

    @BeforeEach
    void init() {
        this.curationQueryService = new CurationQueryService(curationQueryRepository);
    }

    @DisplayName("단일 무드, 술 태그를 조회하는 로직을 조회한다")
    @Test
    void find_all_curations_by_locationRange() {
        //given
        List<CurationListLookupResponse> curationListLookupRespons = initCurationLookupResponses();

        given(curationQueryRepository.findAllCurationsInLocationRange(any())).willReturn(curationListLookupRespons);

        //when
        List<CurationListLookupResponse> singleTagCurationListLookupRespons
                = curationQueryService.findAllSingleTagCurationsByLocationRange(new LocationSquareRangeCondition(0, 0, 0, 0));

        //then
        assertThat(singleTagCurationListLookupRespons).hasSize(2);
    }

    private List<CurationListLookupResponse> initCurationLookupResponses() {
        List<CurationListLookupResponse> curationListLookupRespons =new ArrayList<>();

        curationListLookupRespons.add(new CurationListLookupResponse("c01", "url", "title", "content", "mood1", "alcohol1"));
        curationListLookupRespons.add(new CurationListLookupResponse("c01", "url", "title", "content", "mood2", "alcohol1"));
        curationListLookupRespons.add(new CurationListLookupResponse("c01", "url", "title", "content", "mood3", "alcohol1"));
        curationListLookupRespons.add(new CurationListLookupResponse("c01", "url", "title", "content", "mood1", "alcohol1"));
        curationListLookupRespons.add(new CurationListLookupResponse("c01", "url", "title", "content", "mood2", "alcohol2"));
        curationListLookupRespons.add(new CurationListLookupResponse("c01", "url", "title", "content", "mood3", "alcohol3"));

        curationListLookupRespons.add(new CurationListLookupResponse("c02", "url", "title", "content", "mood1", "alcohol1"));
        curationListLookupRespons.add(new CurationListLookupResponse("c02", "url", "title", "content", "mood2", "alcohol1"));
        curationListLookupRespons.add(new CurationListLookupResponse("c02", "url", "title", "content", "mood3", "alcohol1"));
        curationListLookupRespons.add(new CurationListLookupResponse("c02", "url", "title", "content", "mood1", "alcohol1"));
        curationListLookupRespons.add(new CurationListLookupResponse("c02", "url", "title", "content", "mood2", "alcohol2"));
        curationListLookupRespons.add(new CurationListLookupResponse("c02", "url", "title", "content", "mood3", "alcohol3"));

        return curationListLookupRespons;
    }

}
