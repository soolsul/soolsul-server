package com.soolsul.soolsulserver.bar.common.dto.response;

import java.util.List;

public record AddressConvertResponse(
        List<AddressResponse> documents
) {
}
