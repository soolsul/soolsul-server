package com.soolsul.soolsulserver.bar.businees.client;

import org.springframework.web.bind.annotation.RequestParam;

public interface AddressSearchClient<T> {

    T searchAddress(@RequestParam("query") String query);

    T convertAddress(@RequestParam("x") double logitude, @RequestParam("y") double latitude);
}
