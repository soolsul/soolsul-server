package com.soolsul.soolsulserver.bar.facade;

import com.soolsul.soolsulserver.bar.businees.BarAlcoholTagService;
import com.soolsul.soolsulserver.bar.businees.BarMoodTagService;
import com.soolsul.soolsulserver.bar.businees.BarQueryService;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupConditionRequest;
import com.soolsul.soolsulserver.bar.businees.dto.BarLookupServiceConditionRequest;
import com.soolsul.soolsulserver.bar.presentation.dto.FilteredBarsLookupResponse;
import com.soolsul.soolsulserver.menu.alcohol.service.AlcoholCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BarQueryFacade {

    private static final String TAG_NAME_DELIMITER = ",";

    private final BarQueryService barQueryService;
    private final BarAlcoholTagService barAlcoholTagService;
    private final BarMoodTagService barMoodTarService;
    private final AlcoholCategoryService alcoholCategoryService;

    @Transactional(readOnly = true)
    public FilteredBarsLookupResponse findBarMeetingConditions(BarLookupConditionRequest barLookupConditionRequest) {
        BarLookupServiceConditionRequest barLookupServiceConditionRequest = new BarLookupServiceConditionRequest(
                barLookupConditionRequest.southWestLongitude(),
                barLookupConditionRequest.southWestLatitude(),
                barLookupConditionRequest.northEastLongitude(),
                barLookupConditionRequest.northEastLatitude(),
                getBarMoodTagIds(barLookupConditionRequest.barMoodTagNames()),
                getBarAlcoholTagIds(barLookupConditionRequest.barAlcoholTagNames())
        );

        return barQueryService.findBarFilteredByConditions(barLookupServiceConditionRequest);
    }

    private List<String> getBarMoodTagIds(String barMoodTagNames) {
        List<String> parsedMoodTagNames = parseTagNames(barMoodTagNames);
        return barMoodTarService.findBarAlcoholTagIdsByAlcoholNames(parsedMoodTagNames);
    }

    private List<String> getBarAlcoholTagIds(@NotEmpty String alcoholTagNames) {
        List<String> alcoholCategoryNames = parseTagNames(alcoholTagNames);
        List<String> alcoholCategoryIds
                = alcoholCategoryService.findAlcoholCategoryIdsByAlcoholCategoryNames(alcoholCategoryNames);
        return barAlcoholTagService.findBarAlcoholTagIdsByAlcoholCategoryIds(alcoholCategoryIds);
    }

    private List<String> parseTagNames(String tagNames) {
        return Arrays.stream(tagNames.split(TAG_NAME_DELIMITER)).toList();
    }


}
