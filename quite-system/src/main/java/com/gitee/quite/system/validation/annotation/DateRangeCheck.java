package com.gitee.quite.system.validation.annotation;

import com.gitee.quite.system.validation.validator.DateRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 数据范围的参数校验.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRangeCheck {
    
    String message() default "{permission.config.can.not.all.empty}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}