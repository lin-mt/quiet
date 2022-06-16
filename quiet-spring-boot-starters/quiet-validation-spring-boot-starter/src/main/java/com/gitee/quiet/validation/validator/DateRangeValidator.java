/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.validation.validator;

import com.gitee.quiet.validation.annotation.DateRangeCheck;
import com.gitee.quiet.validation.entity.DateRange;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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