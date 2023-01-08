package com.soolsul.soolsulserver.bar.presentation;

import com.soolsul.soolsulserver.bar.common.dto.request.BarLookupConditionRequest;
import com.soolsul.soolsulserver.bar.common.dto.response.FilteredBarsLookupResponse;
import com.soolsul.soolsulserver.bar.facade.BarQueryFacade;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Locale;

import static com.soolsul.soolsulserver.common.response.ResponseCodes.BAR_LOOK_UP_SUCCESS;

@RestController
@RequestMapping("/api/bars")
@RequiredArgsConstructor
@Validated
public class BarQueryController {

    private final BarQueryFacade barQueryFacade;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<BaseResponse<FilteredBarsLookupResponse>> findBarFilteredByConditions(
            @RequestParam double latitude, // 위도
            @RequestParam double longitude, // 경도
            @RequestParam(defaultValue = "7") int level, //확대 레벨
            @RequestParam @NotNull String barMoodTagNames,
            @RequestParam @NotNull String barAlcoholTagNames,
            Locale locale
    ) {
        BarLookupConditionRequest barLookupConditionRequest = new BarLookupConditionRequest(
                latitude,
                longitude,
                level,
                barMoodTagNames,
                barAlcoholTagNames
        );

        FilteredBarsLookupResponse filteredBarsLookupResponse = barQueryFacade.findBarFilteredByConditions(
                barLookupConditionRequest
        );

        String message = messageSource.getMessage(BAR_LOOK_UP_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<FilteredBarsLookupResponse> baseResponse = new BaseResponse<>(
                BAR_LOOK_UP_SUCCESS.getCode(),
                message,
                filteredBarsLookupResponse
        );

        return ResponseEntity.ok(baseResponse);
    }

}
