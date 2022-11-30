package com.soolsul.soolsulserver.curation.facade;

import com.soolsul.soolsulserver.bar.businees.BarQueryService;
import com.soolsul.soolsulserver.bar.businees.BarSnackMenuService;
import com.soolsul.soolsulserver.bar.businees.dto.BarSnackMenuResponse;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupResponse;
import com.soolsul.soolsulserver.curation.business.CurationQueryService;
import com.soolsul.soolsulserver.curation.dto.CurationDetailLookupResponse;
import com.soolsul.soolsulserver.curation.dto.CurationListLookupResponse;
import com.soolsul.soolsulserver.curation.dto.CurationLookupResponse;
import com.soolsul.soolsulserver.curation.dto.CurationPostLookupResponse;
import com.soolsul.soolsulserver.curation.dto.CurationsLookupResponse;
import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.response.LocationSquareRangeCondition;
import com.soolsul.soolsulserver.location.service.LocationRangeService;
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
