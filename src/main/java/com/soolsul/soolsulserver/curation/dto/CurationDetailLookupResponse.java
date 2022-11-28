package com.soolsul.soolsulserver.curation.dto;

import java.util.List;

public record CurationDetailLookupResponse(
        String curationTitle,
        String curationContent,
        String barSimpleAddress,
        String barConcreteAddress,
        String phoneNumber,
        BarOpeningHoursResponse barOpeningHoursResponse,
        List<BarSnackMenuResponse> barSnackMenuResponses,
        List<CurationPostLookupResponse> curationPostLookupResponses
) {
}
