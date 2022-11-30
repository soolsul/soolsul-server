package com.soolsul.soolsulserver.bar.presentation.dto;

import com.soolsul.soolsulserver.bar.businees.dto.BarStreetNameAddressResponse;
import com.soolsul.soolsulserver.curation.dto.BarOpeningHoursResponse;

public record BarLookupResponse(
        String id,
        String regionId,
        String barCategoryId,
        String name,
        String description,
        String phoneNumber,
        BarStreetNameAddressResponse barStreetNameAddressResponse,
        BarOpeningHoursResponse barOpeningHoursResponse,
        LocationLookupResponse locationLookupResponse
) {
}
