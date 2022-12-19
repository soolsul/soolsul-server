package com.soolsul.soolsulserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SoolsulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoolsulServerApplication.class, args);
    }

}
