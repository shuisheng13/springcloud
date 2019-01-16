package com.pactera.valid;

import com.pactera.constant.ConstantUtlis;
import com.pactera.valid.annotation.ThemeStatus;
import org.springframework.stereotype.Component;

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

        return Arrays.stream(new int[]{ConstantUtlis.themeStatus.DELETE.getCode(),
                ConstantUtlis.themeStatus.ON_SHELF.getCode(),
                ConstantUtlis.themeStatus.DOWN_SHELF.getCode(),
                ConstantUtlis.themeStatus.FORBIDDEN.getCode()}).anyMatch(v->v==value);
    }
}
