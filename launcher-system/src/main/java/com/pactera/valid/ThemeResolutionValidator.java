package com.pactera.valid;

import com.pactera.valid.annotation.ThemeResolution;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName ThemeStatusValidator
 * @Description
 * @Author xukj
 * @Date 2019/1/15 10:55
 * @Version
 */
public class ThemeResolutionValidator implements ConstraintValidator<ThemeResolution, Integer[]>  {
    @Override
    public void initialize(ThemeResolution constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer[] value, ConstraintValidatorContext context) {

        if(null != value) {
            if(value.length != 2) {
                return false;
            }
        }
        return true;
    }
}
