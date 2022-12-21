package com.soolsul.soolsulserver.bar.common.dto.response;

import java.util.Collections;
import java.util.List;

public record AddressLookupResponse(
        List<AddressResponse> addressResponses
) {
    @Override
    public List<AddressResponse> addressResponses() {
        return Collections.unmodifiableList(addressResponses);
    }
}
