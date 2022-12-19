package com.soolsul.soolsulserver.bar.businees.client;

import com.soolsul.soolsulserver.bar.businees.client.dto.KakaoAddressResponse;
import com.soolsul.soolsulserver.common.config.client.KakaoClientAuthHeaderConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "local-address-client", url = "${api.kakao.api-url}",
        configuration = {KakaoClientAuthHeaderConfiguration.class})
public interface KakaoAddressSearchClient {

    @GetMapping("/v2/local/search/address.json")
    KakaoAddressResponse searchAddress(@RequestParam("query") String query);

    @GetMapping("/v2/local/geo/coord2address.json")
    KakaoAddressResponse convertAddress(@RequestParam("x") double logitude, @RequestParam("y") double latitude);
}
