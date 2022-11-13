package com.soolsul.soolsulserver.bar.dto.response;

import java.util.List;

public record BarsLookupResponse (
        List<BarLookupResponse> barLookupResponses
) {
}
