package com.soolsul.soolsulserver.user.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {

    /**
     * 엄밀한 Type검증을 하고 싶다면 값을 true로 설정할 것
     *
     * @return Boolean
     */
    boolean errorOnInvalidType() default false;
}
