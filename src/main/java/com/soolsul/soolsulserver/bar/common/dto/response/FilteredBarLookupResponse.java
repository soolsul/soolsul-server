package com.soolsul.soolsulserver.bar.common.dto.response;

import java.time.LocalDateTime;

public record FilteredBarLookupResponse(
        String barId,
        String barName,
        String barDescription,
        double latitude,
        double longitude,
        String alcoholTagId,
        String alcoholTagName,
        String alcoholMoodId,
        String moodTagName,
        LocalDateTime createdAt
) {

}
