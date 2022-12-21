package com.soolsul.soolsulserver.bar.businees.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoAddressResponse {
    private List<KakaoAddressDocumentResponse> documents;
}
