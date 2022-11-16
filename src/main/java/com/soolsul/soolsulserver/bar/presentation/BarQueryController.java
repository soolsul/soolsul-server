package com.soolsul.soolsulserver.bar.presentation;

import com.soolsul.soolsulserver.bar.presentation.dto.FilteredBarsLookupResponse;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupConditionRequest;
import com.soolsul.soolsulserver.bar.facade.BarQueryFacade;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/bar")
@RequiredArgsConstructor
@Validated
public class BarQueryController {

    private final BarQueryFacade barQueryFacade;

    @GetMapping
    public ResponseEntity<BaseResponse<FilteredBarsLookupResponse>> findBarFilteredByConditions (
            @RequestParam @NotEmpty double southWestLongitude,
            @RequestParam @NotEmpty double southWestLatitude,
            @RequestParam @NotEmpty double northEastLongitude,
            @RequestParam @NotEmpty double northEastLatitude,
            @RequestParam @NotEmpty String barMoodTagNames,
            @RequestParam @NotEmpty String barAlcoholTagNames
    ) {
        BarLookupConditionRequest barLookupConditionRequest = new BarLookupConditionRequest(
                southWestLongitude,
                southWestLatitude,
                northEastLongitude,
                northEastLatitude,
                barMoodTagNames,
                barAlcoholTagNames
        );

        FilteredBarsLookupResponse filteredBarsLookupResponse = barQueryFacade.findBarFilteredByConditions(barLookupConditionRequest);
        BaseResponse<FilteredBarsLookupResponse> baseResponse = new BaseResponse<>(
                ResponseCodeAndMessages.BAR_LOOK_UP_SUCCESS, filteredBarsLookupResponse
        );

        return ResponseEntity.ok(baseResponse);
    }

}
