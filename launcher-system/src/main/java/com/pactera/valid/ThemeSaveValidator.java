package com.pactera.valid;

import com.pactera.po.LaunThemeSavePo;
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
    public boolean supports(Class<?>aClass) {
        return LaunThemeSavePo.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty","主题不能为空");
        ValidationUtils.rejectIfEmpty(errors, "longResolution", "longResolution.empty","分辨率长不能为空");
        ValidationUtils.rejectIfEmpty(errors, "wideResolution", "wideResolution.empty","分辨率宽不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "version", "version.empty","版本id不能为空");
        ValidationUtils.rejectIfEmpty(errors, "filesJson", "filesJson.empty","预览图不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipUrl", "zipUrl.empty","压缩包路径不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.empty","描述不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "author.empty","作者不能为空");
        ValidationUtils.rejectIfEmpty(errors, "releaseTime", "releaseTime.empty","发布时间不能为空");
        ValidationUtils.rejectIfEmpty(errors, "typeId", "typeId.empty","主题分类不能为空");
        LaunThemeSavePo launThemeSavePo = (LaunThemeSavePo) obj;
        //if (p.getAge() < 0) {
        //    e.rejectValue("age", "negativevalue");
        //} else if (p.getAge() > 110) {
        //    e.rejectValue("age", "too.darn.old");
        //}
    }
}
