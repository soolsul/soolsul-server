package com.soolsul.soolsulserver.curation.dto;

public record CurationListLookupResponse(
        String curationId,
        String mainPictureUrl,
        String title,
        String content,
        String barMoodTagName,
        String barAlcoholTagName
) {
}
