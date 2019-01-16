package com.pactera.valid.annotation;

/**
 * @ClassName ThemeStatus
 * @Description
 * @Author xukj
 * @Date 2019/1/15 10:54
 * @Version
 */

import com.pactera.valid.ThemeStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must not be {@code null}.
 * Accepts any type.
 *
 * @author Emmanuel Bernard
 */
@Target({ PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { ThemeStatusValidator.class})
public @interface ThemeStatus {

    String message() default "status数值非系统中设定有效数值";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
