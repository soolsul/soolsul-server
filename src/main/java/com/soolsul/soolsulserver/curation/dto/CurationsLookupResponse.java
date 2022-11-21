package com.soolsul.soolsulserver.curation.dto;

import java.util.List;

public record CurationsLookupResponse(
        List<CurationLookupResponse> curationLookupResponses
) {
}
