package com.soolsul.soolsulserver.bar.presentation.dto;

import com.soolsul.soolsulserver.bar.businees.dto.BarLookupResponse;

import java.util.List;

public record BarsLookupResponse (
        List<BarLookupResponse> barLookupResponses
) {
}
