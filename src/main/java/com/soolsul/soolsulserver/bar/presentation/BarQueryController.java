package com.soolsul.soolsulserver.bar.presentation;

import com.soolsul.soolsulserver.bar.dto.response.BarsLookupResponse;
import com.soolsul.soolsulserver.bar.dto.request.BarLookupConditionRequest;
import com.soolsul.soolsulserver.bar.facade.BarQueryFacade;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bar")
@RequiredArgsConstructor
public class BarQueryController {

    private final BarQueryFacade barQueryFacade;

    @GetMapping
    public ResponseEntity<BaseResponse<BarsLookupResponse>> findBarMeetingConditions (
            @Valid @RequestParam BarLookupConditionRequest barLookupConditionRequest
    ) {
        BarsLookupResponse barsLookupResponse = barQueryFacade.findBarMeetingConditions(barLookupConditionRequest);
        BaseResponse<BarsLookupResponse> baseResponse = new BaseResponse<>(
                ResponseCodeAndMessages.BAR_LOOK_UP_SUCCESS, barsLookupResponse
        );

        return ResponseEntity.ok(baseResponse);
    }

}
