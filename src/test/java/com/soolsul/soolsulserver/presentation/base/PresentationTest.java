package com.soolsul.soolsulserver.presentation.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

import com.soolsul.soolsulserver.bar.presentation.BarQueryController;
import com.soolsul.soolsulserver.curation.presentation.CurationQueryController;

@Import(PresentationTestConfig.class)
@WebMvcTest(value = {CurationQueryController.class, BarQueryController.class},
	excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)}, //security 설정을 종료하기 위한 설정
	excludeAutoConfiguration = SecurityAutoConfiguration.class //security auto configuration 을 종료하기 위한 설정
)
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface PresentationTest {
}
