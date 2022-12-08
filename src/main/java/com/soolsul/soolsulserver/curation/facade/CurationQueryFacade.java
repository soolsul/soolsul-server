package com.soolsul.soolsulserver.curation.facade;

import com.soolsul.soolsulserver.bar.businees.BarQueryService;
import com.soolsul.soolsulserver.bar.businees.BarSnackMenuService;
import com.soolsul.soolsulserver.bar.common.dto.response.BarSnackMenuResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.BarLookupResponse;
import com.soolsul.soolsulserver.curation.business.CurationQueryService;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationDetailLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationListLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationPostLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationsLookupResponse;
import com.soolsul.soolsulserver.location.common.dto.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.common.dto.response.LocationSquareRangeCondition;
import com.soolsul.soolsulserver.location.business.LocationRangeService;
import com.soolsul.soolsulserver.post.business.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurationQueryFacade {

    private final LocationRangeService locationRangeService;
    private final CurationQueryService curationQueryService;
    private final BarQueryService barQueryService;
    private final BarSnackMenuService barSnackMenuService;
    private final PostQueryService postQueryService;

    public CurationsLookupResponse findAllCurationsByLocationRange(LocationSquareRangeRequest locationSquareRangeRequest) {
        LocationSquareRangeCondition locationSquareRangeCondition = locationRangeService.calculateLocationSquareRange(
                locationSquareRangeRequest);

        List<CurationListLookupResponse> singleTagCurationListLookupRespons
                = curationQueryService.findAllSingleTagCurationsByLocationRange(locationSquareRangeCondition);

        return new CurationsLookupResponse(singleTagCurationListLookupRespons);
    }

    public CurationDetailLookupResponse findCurationDetailsByCurationId(String curationId) {
        CurationLookupResponse curationLookupResponse = curationQueryService.findCurationByCurationId(curationId);
        BarLookupResponse barLookupResponse = barQueryService.findById(curationLookupResponse.barId());
        List<BarSnackMenuResponse> barSnackMenuResponses = barSnackMenuService.findAllBarSnackMenuByBarId(barLookupResponse.id());
        List<CurationPostLookupResponse> curationPostLookupResponses = postQueryService.findAllPostByBarId(barLookupResponse.id());

        return new CurationDetailLookupResponse(
                curationLookupResponse.curationTitle(),
                curationLookupResponse.curationContent(),
                barLookupResponse.phoneNumber(),
                barLookupResponse.barStreetNameAddressResponse(),
                barLookupResponse.barOpeningHoursResponse(),
                barSnackMenuResponses,
                curationPostLookupResponses
        );

    }

}
