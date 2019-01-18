package com.pactera.valid;

import com.pactera.constant.ConstantUtlis;
import com.pactera.valid.annotation.ThemeStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @ClassName ThemeStatusValidator
 * @Description
 * @Author xukj
 * @Date 2019/1/15 10:55
 * @Version
 */
public class ThemeStatusValidator implements ConstraintValidator<ThemeStatus, Integer>  {
    @Override
    public void initialize(ThemeStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        if(null == value) {
            return false;
        }

        ConstantUtlis.themeStatus[] themeStatuses = ConstantUtlis.themeStatus.values();
        return Arrays.stream(themeStatuses).anyMatch(v->v.getCode()==value);
    }
}
