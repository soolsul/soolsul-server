package com.soolsul.soolsulserver.curation.common.dto.response;

import com.soolsul.soolsulserver.bar.common.dto.response.BarSnackMenuResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.BarStreetNameAddressResponse;

import java.util.List;

public record CurationDetailLookupResponse(
        String curationTitle,
        String curationContent,
        String phoneNumber,
        BarStreetNameAddressResponse barStreetNameAddressResponse,
        BarOpeningHoursResponse barOpeningHoursResponse,
        List<BarSnackMenuResponse> barSnackMenuResponses,
        List<CurationPostLookupResponse> curationPostLookupResponses
) {
}
