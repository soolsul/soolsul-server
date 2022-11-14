package com.soolsul.soolsulserver.bar.presentation.dto;

public record BarLookupResponse(
        String id,
        String regionId,
        String barCategoryId,
        String name,
        String description,
        LocationLookupResponse locationLookupResponse
) {
}
