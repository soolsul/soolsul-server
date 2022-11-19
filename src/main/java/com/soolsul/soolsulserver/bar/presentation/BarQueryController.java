package com.soolsul.soolsulserver.bar.presentation;

import com.soolsul.soolsulserver.bar.presentation.dto.FilteredBarsLookupResponse;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupConditionRequest;
import com.soolsul.soolsulserver.bar.facade.BarQueryFacade;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.common.userlocation.UserLocation;
import com.soolsul.soolsulserver.common.userlocation.UserLocationBasedSquareRange;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/bars")
@RequiredArgsConstructor
@Validated
public class BarQueryController {

    private final BarQueryFacade barQueryFacade;

    @GetMapping
    public ResponseEntity<BaseResponse<FilteredBarsLookupResponse>> findBarFilteredByConditions(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "3") int level,
            @RequestParam @NotNull String barMoodTagNames,
            @RequestParam @NotNull String barAlcoholTagNames
    ) {
        UserLocation userLocation = UserLocation.of(latitude, longitude, level);
        UserLocationBasedSquareRange squareRange = new UserLocationBasedSquareRange(userLocation);

        BarLookupConditionRequest barLookupConditionRequest = new BarLookupConditionRequest(
                squareRange.getMaxX(),
                squareRange.getMaxY(),
                squareRange.getMinX(),
                squareRange.getMinY(),
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
