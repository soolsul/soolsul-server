package com.soolsul.soolsulserver.curation.common.dto.response;

import java.util.List;

public record CurationsLookupResponse(
        List<CurationListLookupResponse> curationListLookupResponses
) {
}
