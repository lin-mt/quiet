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

package com.gitee.quiet.validation.annotation;

import com.gitee.quiet.validation.validator.DateRangeValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 时间范围的参数校验.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRangeCheck {

    String message() default "{quiet.validation.date-range.start.should.before.end}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}