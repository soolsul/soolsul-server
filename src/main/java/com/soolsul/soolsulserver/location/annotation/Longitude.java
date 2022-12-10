package com.soolsul.soolsulserver.location.annotation;

import com.soolsul.soolsulserver.location.validator.LongitudeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = LongitudeValidator.class)
@Documented
public @interface Longitude {

    String message() default "Invalid Longitude";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
