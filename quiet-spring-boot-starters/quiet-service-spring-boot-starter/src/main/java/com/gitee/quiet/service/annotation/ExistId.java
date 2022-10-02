/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.service.annotation;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.service.validator.ExistIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 数据库ID校验校验.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ExistIdValidator.class)
public @interface ExistId {

  String message() default "{quiet.validation.entity.idNotExist}";

  Class<? extends QuietRepository<?>> repository();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
