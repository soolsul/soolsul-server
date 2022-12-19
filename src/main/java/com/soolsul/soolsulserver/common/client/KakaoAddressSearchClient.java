package com.soolsul.soolsulserver.common.client;

import com.soolsul.soolsulserver.common.client.dto.response.KakaoAddressSearchResponse;
import com.soolsul.soolsulserver.common.config.client.KakaoClientAuthHeaderConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "local-address-client", url = "${api.kakao.api-url}",
        configuration = {KakaoClientAuthHeaderConfiguration.class})
public interface KakaoAddressSearchClient {

    @GetMapping("/v2/local/search/address.json")
    KakaoAddressSearchResponse searchAddress(@RequestParam("query") String query);
}
