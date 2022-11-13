package com.soolsul.soolsulserver.bar.businees.dto;

import java.time.LocalDateTime;

public record BarLookupResponse (
        String barId,
        String barName,
        String barDescription,
        String alcoholTagId,
        String alcoholTagName,
        String alcoholMoodId,
        String moodTagName,
        LocalDateTime createdAt
) {

}
