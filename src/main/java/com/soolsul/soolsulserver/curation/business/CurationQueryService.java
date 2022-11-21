package com.soolsul.soolsulserver.curation.business;

import com.soolsul.soolsulserver.curation.dto.CurationLookupResponse;
import com.soolsul.soolsulserver.curation.persistence.CurationQueryRepository;
import com.soolsul.soolsulserver.location.response.LocationSquareRangeCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurationQueryService {

    private final CurationQueryRepository curationQueryRepository;

    public List<CurationLookupResponse> findAllSingleTagCurationsByLocationRange(
            LocationSquareRangeCondition locationSquareRangeCondition
    ) {
        List<CurationLookupResponse> curationLookupResponses
                = curationQueryRepository.findAllCurationsInLocationRange(locationSquareRangeCondition);

        Map<String, Boolean> curationIdMap = createCurationIdMap(curationLookupResponses);
        return getFilteredSingleTagCuration(curationLookupResponses, curationIdMap);
    }

    private List<CurationLookupResponse> getFilteredSingleTagCuration(
            List<CurationLookupResponse> curationLookupResponses,
            Map<String, Boolean> curationIdMap
    ) {
        List<CurationLookupResponse> filteredSingleTagNameCurations = new ArrayList<>();

        for (CurationLookupResponse curationLookupResponse : curationLookupResponses) {
            String curationId = curationLookupResponse.curationId();
            Boolean alreadyAddedCuration = curationIdMap.get(curationId);

            if(!alreadyAddedCuration) {
                filteredSingleTagNameCurations.add(curationLookupResponse);
                curationIdMap.put(curationId, true);
            }
        }

        return filteredSingleTagNameCurations;
    }

    // createCurationIdMap : 단일 태그 큐레이션을 추가하기 위한 함수 (key : curationId, Boolean : curation 추가 여부를 확인하는 변수)
    private Map<String, Boolean> createCurationIdMap(List<CurationLookupResponse> curationLookupResponses) {
        Map<String, Boolean> curationIdMap = new HashMap<>();
        for (CurationLookupResponse curationLookupResponse : curationLookupResponses) {
            curationIdMap.putIfAbsent(curationLookupResponse.curationId(), false);
        }
        return curationIdMap;
    }

}
