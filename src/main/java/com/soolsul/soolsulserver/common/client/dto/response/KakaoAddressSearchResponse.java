package com.soolsul.soolsulserver.common.client.dto.response;

import com.soolsul.soolsulserver.bar.businees.client.dto.KakaoAddressDocumentResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoAddressSearchResponse {
    private List<KakaoAddressDocumentResponse> documents;
}
