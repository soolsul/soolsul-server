package com.soolsul.soolsulserver.bar.businees;

import com.soolsul.soolsulserver.bar.businees.dto.BarLookupResponse;
import com.soolsul.soolsulserver.bar.businees.dto.BarLookupServiceConditionRequest;
import com.soolsul.soolsulserver.bar.presentation.dto.BarsLookupResponse;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BarQueryService {

    private final BarQueryRepository barQueryRepository;

    public BarsLookupResponse findBarFilteredByConditions(
            @Valid BarLookupServiceConditionRequest barLookupServiceConditionRequest
    ) {
        List<BarLookupResponse> barLookupResponses
                = barQueryRepository.findBarMeetingConditions(barLookupServiceConditionRequest);
        return new BarsLookupResponse(barLookupResponses);
    }

}
