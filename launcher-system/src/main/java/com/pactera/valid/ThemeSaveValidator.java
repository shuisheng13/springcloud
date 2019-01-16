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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "主题名称不能为空");
        ValidationUtils.rejectIfEmpty(errors, "longResolution", "分辨率长不能为空");
        ValidationUtils.rejectIfEmpty(errors, "wideResolution", "分辨率宽不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "version", "版本id不能为空");
        ValidationUtils.rejectIfEmpty(errors, "filesJson", "预览图不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipUrl", "压缩包路径不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "描述不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "作者不能为空");
        ValidationUtils.rejectIfEmpty(errors, "releaseTime", "发布时间不能为空");

        LaunThemeSaveVo p = (LaunThemeSaveVo) obj;
        //if (p.getAge() < 0) {
        //    e.rejectValue("age", "negativevalue");
        //} else if (p.getAge() > 110) {
        //    e.rejectValue("age", "too.darn.old");
        //}
    }
}
