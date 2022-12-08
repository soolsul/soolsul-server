package com.soolsul.soolsulserver.curation.common.dto.response;

public record CurationLookupResponse(
        String curationId,
        String mainPictureUrl,
        String curationTitle,
        String curationContent,
        String barId
) {
}
