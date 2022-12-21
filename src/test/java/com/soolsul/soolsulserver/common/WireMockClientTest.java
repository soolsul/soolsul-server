package com.soolsul.soolsulserver.common;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 애너테이션 사용방식 원문 : https://syaku.tistory.com/387
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "api.kakao.api-url=http://localhost:${wiremock.server.port}"
})
@ExtendWith(ClientMocksExtension.class)
public @interface WireMockClientTest {
}


