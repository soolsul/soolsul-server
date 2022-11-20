package com.soolsul.soolsulserver.curation.facade;

import com.soolsul.soolsulserver.curation.dto.CurationsLookupResponse;
import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurationQueryFacade {



    public CurationsLookupResponse findAllCurationsByLocationRange(LocationSquareRangeRequest locationSquareRangeRequest) {
        return null;
    }
}
