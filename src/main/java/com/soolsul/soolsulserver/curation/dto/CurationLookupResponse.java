package com.soolsul.soolsulserver.curation.dto;

public record CurationLookupResponse(
        String curationId,
        String mainPictureUrl,
        String title,
        String content,
        String barMoodTagName,
        String barAlcoholTagName
) {
}
