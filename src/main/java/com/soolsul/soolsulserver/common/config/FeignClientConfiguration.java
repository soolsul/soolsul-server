package com.soolsul.soolsulserver.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.soolsul.soolsulserver.*")
public class FeignClientConfiguration {
}
