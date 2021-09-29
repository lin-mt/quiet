/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.validation.validator;

import com.gitee.quiet.validation.annotation.DateRangeCheck;
import com.gitee.quiet.validation.entity.DateRange;

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