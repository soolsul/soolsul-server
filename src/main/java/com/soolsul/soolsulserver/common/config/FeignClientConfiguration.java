package com.soolsul.soolsulserver.common.config;

import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "com.soolsul.soolsulserver.*")
public class FeignClientConfiguration {

    /**
     * - @ref : https://mangkyu.tistory.com/279
     * - Retryer 을 사용하면 period, timeout 을 설정할 수 있음.
     */
    @Bean
    Retryer retryer() {
        // 0.5초의 간격으로 시작해 최대 3초의 간격으로 점점 증가하며, 최대5번 재시도한다.
        return new Retryer.Default(500L, TimeUnit.SECONDS.toMillis(3L), 5);
    }
}
