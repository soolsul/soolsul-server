package com.soolsul.soolsulserver.bar.businees.client;

import com.soolsul.soolsulserver.bar.common.dto.request.AddressLookupRequest;
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
    public void lookup_address_by_query() {
        // given
        String query = "전북 삼성동 100";
        AddressLookupRequest request = new AddressLookupRequest(query);

        // when
        AddressLookupResponse lookupResponse = placeApiService.searchAddress(request);

        // then
        assertThat(lookupResponse.addressResponses())
                .isNotEmpty()
                .allSatisfy(address ->
                        assertThat(address).satisfiesAnyOf(addr -> assertThat(addr.addressName()).contains("전북"))
                );
    }
}
