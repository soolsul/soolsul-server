package com.soolsul.soolsulserver.curation.dto;

import com.soolsul.soolsulserver.bar.businees.dto.BarSnackMenuResponse;
import com.soolsul.soolsulserver.bar.businees.dto.BarStreetNameAddressResponse;

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
