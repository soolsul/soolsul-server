package com.soolsul.soolsulserver.curation.common.dto.response;

public record CurationListLookupResponse(
        String curationId,
        String mainPictureUrl,
        String title,
        String content,
        String barMoodTagName,
        String barAlcoholTagName
) {
}
