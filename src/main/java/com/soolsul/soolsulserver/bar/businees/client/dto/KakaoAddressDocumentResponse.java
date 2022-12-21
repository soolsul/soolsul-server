package com.soolsul.soolsulserver.bar.businees.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoAddressDocumentResponse {
    private KakaoSearchAddressResponse address;
    private KakaoSearchRoadAddressResponse roadAddress;

    public KakaoSearchRoadAddressResponse getRoadAddress() {
        if (roadAddress == null) {
            return KakaoSearchRoadAddressResponse.defaultResponse();
        }
        return roadAddress;
    }
}
