package com.soolsul.soolsulserver.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.apache.commons.codec.CharEncoding.UTF_8;

@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultLocale(Locale.US);
        messageSource.setDefaultEncoding(UTF_8);
        return messageSource;
    }

}
