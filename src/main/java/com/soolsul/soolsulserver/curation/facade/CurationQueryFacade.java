package com.soolsul.soolsulserver.curation.facade;

import com.soolsul.soolsulserver.curation.business.CurationQueryService;
import com.soolsul.soolsulserver.curation.dto.CurationLookupResponse;
import com.soolsul.soolsulserver.curation.dto.CurationsLookupResponse;
import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.response.LocationSquareRangeCondition;
import com.soolsul.soolsulserver.location.service.LocationRangeService;
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

    public CurationsLookupResponse findAllCurationsByLocationRange(LocationSquareRangeRequest locationSquareRangeRequest) {
        LocationSquareRangeCondition locationSquareRangeCondition = locationRangeService.calculateLocationSquareRange(
                locationSquareRangeRequest);

        List<CurationLookupResponse> singleTagCurationLookupResponses
                = curationQueryService.findAllSingleTagCurationsByLocationRange(locationSquareRangeCondition);

        return new CurationsLookupResponse(singleTagCurationLookupResponses);
    }

}
