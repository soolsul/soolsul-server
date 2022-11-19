package com.soolsul.soolsulserver.bar.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soolsul.soolsulserver.bar.businees.dto.FilteredBarLookupResponse;

import java.util.List;

public record FilteredBarsLookupResponse(
        @JsonProperty("barList") List<FilteredBarLookupResponse> filteredBarLookupResponse
) {
}
