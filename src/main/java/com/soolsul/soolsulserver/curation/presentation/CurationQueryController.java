package com.soolsul.soolsulserver.curation.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.curation.dto.CurationDetailLookupResponse;
import com.soolsul.soolsulserver.curation.dto.CurationsLookupResponse;
import com.soolsul.soolsulserver.curation.facade.CurationQueryFacade;
import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curations")
public class CurationQueryController {

    private final CurationQueryFacade curationQueryFacade;

    @GetMapping
    public ResponseEntity<BaseResponse<CurationsLookupResponse>> findAllCurationsByLocationRange(
            @RequestParam @NotEmpty double latitude, // 위도 (Y)
            @RequestParam @NotEmpty double longitude, // 경도 (X)
            @RequestParam(defaultValue = "3") int level
    ) {
        LocationSquareRangeRequest locationSquareRangeRequest = new LocationSquareRangeRequest(latitude, longitude, level);

        CurationsLookupResponse curationsLookupResponse = curationQueryFacade.findAllCurationsByLocationRange(
                locationSquareRangeRequest
        );

        BaseResponse<CurationsLookupResponse> baseResponse = new BaseResponse<>(
                ResponseCodeAndMessages.CURATIONS_LOOK_UP_SUCCESS,
                curationsLookupResponse
        );

        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{curationId}")
    public ResponseEntity<BaseResponse<CurationDetailLookupResponse>> findCurationDetailsByCurationId (
            @PathVariable String curationId
    ) {
        CurationDetailLookupResponse curationDetailLookupResponse
                = curationQueryFacade.findCurationDetailsByCurationId(curationId);

        BaseResponse<CurationDetailLookupResponse> baseResponse = new BaseResponse<>(
                ResponseCodeAndMessages.CURATION_DETAILS_LOOK_UP_SUCCESS,
                curationDetailLookupResponse
        );

        return ResponseEntity.ok(baseResponse);
    }

}
