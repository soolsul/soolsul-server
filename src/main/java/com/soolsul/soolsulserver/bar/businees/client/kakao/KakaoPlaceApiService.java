package com.soolsul.soolsulserver.bar.businees.client.kakao;

import com.soolsul.soolsulserver.bar.businees.client.PlaceApiService;
import com.soolsul.soolsulserver.bar.businees.client.dto.KakaoAddressResponse;
import com.soolsul.soolsulserver.bar.businees.client.dto.KakaoSearchAddressResponse;
import com.soolsul.soolsulserver.bar.businees.client.dto.KakaoSearchRoadAddressResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.AddressConvertResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.AddressLookupResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.AddressResponse;
import com.soolsul.soolsulserver.bar.exception.InvalidAddressException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoPlaceApiService implements PlaceApiService {

    private final KakaoAddressSearchClient addressSearchClient;

    @Override
    public AddressLookupResponse searchAddress(String query) {
        validateAddressName(query);
        KakaoAddressResponse addressResponse = addressSearchClient.searchAddress(query);
        return new AddressLookupResponse(buildAddressResponseList(addressResponse));
    }

    @Override
    public AddressConvertResponse convertAddress(double longitude, double latitude) {
        KakaoAddressResponse convertResponse = addressSearchClient.convertAddress(longitude, latitude);
        return new AddressConvertResponse(buildAddressResponseList(convertResponse));
    }

    private void validateAddressName(String addressName) {
        if (addressName == null || addressName.isBlank()) {
            throw new InvalidAddressException();
        }
    }

    private List<AddressResponse> buildAddressResponseList(
            KakaoAddressResponse addressResponse) {
        return addressResponse.getDocuments()
                .stream()
                .map(document -> getAddressResponse(document.getAddress(), document.getRoadAddress()))
                .toList();
    }

    private AddressResponse getAddressResponse(KakaoSearchAddressResponse address, KakaoSearchRoadAddressResponse roadAddress) {
        return AddressResponse.builder()
                .addressName(address.getAddressName())
                .roadAddressName(roadAddress.getAddressName())
                .region1DepthName(address.getRegion1DepthName())
                .region2DepthName(address.getRegion2DepthName())
                .region3DepthName(address.getRegion3DepthName())
                .region3DepthHName(address.getRegion3DepthHName())
                .mainAddressNo(address.getMainAddressNo())
                .subAddressNo(address.getSubAddressNo())
                .roadName(roadAddress.getRoadName())
                .mainBuildingNo(roadAddress.getMainBuildingNo())
                .subBuildingNo(roadAddress.getSubBuildingNo())
                .build();
    }
}
