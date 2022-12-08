package com.soolsul.soolsulserver.bar.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FilteredBarsLookupResponse(
        @JsonProperty("barList") List<FilteredBarLookupResponse> filteredBarsLookupResponse
) {
}
