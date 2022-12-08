package com.soolsul.soolsulserver.bar.common.dto.response;

import com.soolsul.soolsulserver.curation.common.dto.response.BarOpeningHoursResponse;

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
