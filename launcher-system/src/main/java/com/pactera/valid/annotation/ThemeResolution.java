package com.pactera.valid.annotation;

/**
 * @ClassName ThemeStatus
 * @Description
 * @Author xukj
 * @Date 2019/1/15 10:54
 * @Version
 */

import com.pactera.valid.ThemeResolutionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must not be {@code null}.
 * Accepts any type.
 *
 * @author Emmanuel Bernard
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { ThemeResolutionValidator.class})
public @interface ThemeResolution {

    String message() default "分辨率参数设置有误";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
