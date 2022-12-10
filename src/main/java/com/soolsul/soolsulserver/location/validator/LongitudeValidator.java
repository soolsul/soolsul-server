package com.soolsul.soolsulserver.location.validator;

import com.soolsul.soolsulserver.location.annotation.Longitude;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongitudeValidator implements ConstraintValidator<Longitude, Double> {

    private static final double MIN_LONGITUDE_RANGE = -180.0;
    private static final double MAX_LONGITUDE_RANGE = 180.0;

    @Override
    public void initialize(Longitude constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value)) {
            return false;
        }

        return isValidLongitudeRange(value);
    }

    private boolean isValidLongitudeRange(Double value) {
        return (MIN_LONGITUDE_RANGE <= value) && (value <= MAX_LONGITUDE_RANGE);
    }

}
