package com.soolsul.soolsulserver.curation.dto;

public record CurationLookupResponse(
        String curationId,
        String mainPictureUrl,
        String curationTitle,
        String curationContent,
        String barId
) {
}
