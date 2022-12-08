package com.soolsul.soolsulserver.curation.business;

import com.soolsul.soolsulserver.curation.common.dto.response.CurationListLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationLookupResponse;
import com.soolsul.soolsulserver.curation.persistence.CurationQueryRepository;
import com.soolsul.soolsulserver.location.common.dto.response.LocationSquareRangeCondition;
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

    public List<CurationListLookupResponse> findAllSingleTagCurationsByLocationRange(
            LocationSquareRangeCondition locationSquareRangeCondition
    ) {
        List<CurationListLookupResponse> curationListLookupResponses
                = curationQueryRepository.findAllCurationsInLocationRange(locationSquareRangeCondition);

        Map<String, Boolean> curationIdMap = createCurationIdMap(curationListLookupResponses);
        return getFilteredSingleTagCuration(curationListLookupResponses, curationIdMap);
    }

    private List<CurationListLookupResponse> getFilteredSingleTagCuration(
            List<CurationListLookupResponse> curationListLookupRespons,
            Map<String, Boolean> curationIdMap
    ) {
        List<CurationListLookupResponse> filteredSingleTagNameCurations = new ArrayList<>();

        for (CurationListLookupResponse curationListLookupResponse : curationListLookupRespons) {
            String curationId = curationListLookupResponse.curationId();
            Boolean alreadyAddedCuration = curationIdMap.get(curationId);

            if(!alreadyAddedCuration) {
                filteredSingleTagNameCurations.add(curationListLookupResponse);
                curationIdMap.put(curationId, true);
            }
        }

        return filteredSingleTagNameCurations;
    }

    // createCurationIdMap : 단일 태그 큐레이션을 추가하기 위한 함수 (key : curationId, Boolean : curation 추가 여부를 확인하는 변수)
    private Map<String, Boolean> createCurationIdMap(List<CurationListLookupResponse> curationListLookupResponses) {
        Map<String, Boolean> curationIdMap = new HashMap<>();
        for (CurationListLookupResponse curationListLookupResponse : curationListLookupResponses) {
            curationIdMap.putIfAbsent(curationListLookupResponse.curationId(), false);
        }
        return curationIdMap;
    }

    public CurationLookupResponse findCurationByCurationId(String curationId) {
        return curationQueryRepository.findById(curationId);
    }
}
