package com.pactera.valid;

import com.pactera.vo.LaunThemeSaveVo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @ClassName ThemeSaveValidator
 * @Description
 * @Author xukj
 * @Date 2019/1/15 16:13
 * @Version
 */
@Component
public class ThemeSaveValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return LaunThemeSaveVo.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "title", "主题名称不能为空");
        LaunThemeSaveVo p = (LaunThemeSaveVo) obj;
        //if (p.getAge() < 0) {
        //    e.rejectValue("age", "negativevalue");
        //} else if (p.getAge() > 110) {
        //    e.rejectValue("age", "too.darn.old");
        //}
    }
}
