package com.soolsul.soolsulserver.bar.businees.client;

import com.soolsul.soolsulserver.bar.common.dto.response.AddressConvertResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.AddressLookupResponse;
import com.soolsul.soolsulserver.common.TestRedisContainer;
import com.soolsul.soolsulserver.common.WireMockClientTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@WireMockClientTest
class KakaoPlaceApiServiceTest extends TestRedisContainer {

    @Autowired
    KakaoPlaceApiService placeApiService;

    @DisplayName("주소를 검색을 통해 장소 목록을 조회한다.")
    @Test
    void lookup_address_by_query() {
        // given
        String query = "전북 삼성동 100";

        // when
        AddressLookupResponse lookupResponse = placeApiService.searchAddress(query);

        // then
        assertThat(lookupResponse.addressResponses())
                .isNotEmpty()
                .allSatisfy(address ->
                        assertThat(address).satisfiesAnyOf(addr -> assertThat(addr.addressName()).contains("전북"))
                );
    }

    @DisplayName("위도, 경도를 통해 주소로 변환한다.")
    @Test
    void convert_position_to_address() {
        // given
        double longitude = 127.423084873712;
        double latitude = 37.5145458257413;

        // when
        AddressConvertResponse convertResponse = placeApiService.convertAddress(longitude, latitude);

        // then
        assertThat(convertResponse.documents()).isNotEmpty();
    }
}
