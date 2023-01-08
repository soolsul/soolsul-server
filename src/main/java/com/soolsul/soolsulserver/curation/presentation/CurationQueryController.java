package com.soolsul.soolsulserver.curation.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationDetailLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationsLookupResponse;
import com.soolsul.soolsulserver.curation.facade.CurationQueryFacade;
import com.soolsul.soolsulserver.location.common.dto.request.LocationSquareRangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.Locale;

import static com.soolsul.soolsulserver.common.response.ResponseCodes.CURATIONS_LOOK_UP_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.CURATION_DETAILS_LOOK_UP_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curations")
public class CurationQueryController {

    private final CurationQueryFacade curationQueryFacade;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<BaseResponse<CurationsLookupResponse>> findAllCurationsByLocationRange(
            @RequestParam @NotEmpty double latitude, // 위도 (Y)
            @RequestParam @NotEmpty double longitude, // 경도 (X)
            @RequestParam(defaultValue = "3") int level,
            Locale locale
    ) {
        LocationSquareRangeRequest locationSquareRangeRequest = new LocationSquareRangeRequest(latitude, longitude, level);
        CurationsLookupResponse curationsLookupResponse = curationQueryFacade.findAllCurationsByLocationRange(
                locationSquareRangeRequest
        );

        String message = messageSource.getMessage(CURATIONS_LOOK_UP_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<CurationsLookupResponse> baseResponse = new BaseResponse<>(
                CURATIONS_LOOK_UP_SUCCESS.getCode(),
                message,
                curationsLookupResponse
        );

        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{curationId}")
    public ResponseEntity<BaseResponse<CurationDetailLookupResponse>> findCurationDetailsByCurationId (
            @PathVariable String curationId,
            Locale locale
    ) {
        CurationDetailLookupResponse curationDetailLookupResponse
                = curationQueryFacade.findCurationDetailsByCurationId(curationId);

        String message = messageSource.getMessage(CURATION_DETAILS_LOOK_UP_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<CurationDetailLookupResponse> baseResponse = new BaseResponse<>(
                CURATION_DETAILS_LOOK_UP_SUCCESS.getCode(),
                message,
                curationDetailLookupResponse
        );

        return ResponseEntity.ok(baseResponse);
    }

}
