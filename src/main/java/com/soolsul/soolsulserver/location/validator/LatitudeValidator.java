package com.soolsul.soolsulserver.location.validator;

import com.soolsul.soolsulserver.location.annotation.Latitude;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatitudeValidator implements ConstraintValidator<Latitude, Double> {

    private static final double MIN_LATITUDE_RANGE = -90.0;
    private static final double MAX_LATITUDE_RANGE = 90.0;

    @Override
    public void initialize(Latitude constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(value)) {
            return false;
        }

        return isValidLatitudeRange(value);
    }

    private boolean isValidLatitudeRange(Double value) {
        return (MIN_LATITUDE_RANGE <= value) && (value <= MAX_LATITUDE_RANGE);
    }

}
