package com.soolsul.soolsulserver.bar.presentation.dto;

import com.soolsul.soolsulserver.bar.businees.dto.FilteredBarLookupResponse;

import java.util.List;

public record FilteredBarsLookupResponse(
        List<FilteredBarLookupResponse> filteredBarLookupRespons
) {
}
