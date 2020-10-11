package com.gitee.quite.system.validation.validator;

import com.gitee.quite.system.base.DateRange;
import com.gitee.quite.system.validation.annotation.DateRangeCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 数据范围校验.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DateRangeValidator implements ConstraintValidator<DateRangeCheck, DateRange> {
    
    @Override
    public boolean isValid(DateRange value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            if (Objects.nonNull(value.getStart()) && Objects.nonNull(value.getEnd())) {
                return value.getEnd().isAfter(value.getStart());
            }
        }
        return true;
    }
}