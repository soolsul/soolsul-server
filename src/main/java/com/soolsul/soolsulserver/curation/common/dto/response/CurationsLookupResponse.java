package com.soolsul.soolsulserver.curation.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CurationsLookupResponse(
        @JsonProperty("curationList") List<CurationListLookupResponse> curationListLookupResponses
) {
}
