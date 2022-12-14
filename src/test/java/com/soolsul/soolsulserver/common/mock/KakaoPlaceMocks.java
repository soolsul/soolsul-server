package com.soolsul.soolsulserver.common.mock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

// https://www.baeldung.com/spring-cloud-feign-integration-tests
public abstract class KakaoPlaceMocks {

    private static final String ADDRESS_SEARCH_URL = "/v2/local/search/address.json";
    private static final String ADDRESS_CONVERT_URL = "/v2/local/geo/coord2address.json";

    private static StubMapping addressSearchStub;
    private static StubMapping addressConvertStub;

    public static void startAllMocks() {
        setupSearchAddressStub();
        setupConvertAddressStub();
    }

    public static void removeAllMocks() {
        WireMock.removeStub(addressSearchStub);
        WireMock.removeStub(addressConvertStub);
    }

    private static void setupSearchAddressStub() {
        addressSearchStub = stubFor(get(urlPathEqualTo(ADDRESS_SEARCH_URL))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                        .withBody(getMockResponseBodyByPath("payload/get-kakao-lookup-address-response.json"))
                )
        );
    }

    private static void setupConvertAddressStub() {
        addressConvertStub = stubFor(get(urlPathEqualTo(ADDRESS_CONVERT_URL))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                        .withBody(getMockResponseBodyByPath("payload/get-kakao-convert-address-response.json"))
                )
        );
    }

    private static String getMockResponseBodyByPath(String path) {
        try {
            return copyToString(getMockResourceStream(path), defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static InputStream getMockResourceStream(String path) {
        return KakaoPlaceMocks.class.getClassLoader().getResourceAsStream(path);
    }
}
