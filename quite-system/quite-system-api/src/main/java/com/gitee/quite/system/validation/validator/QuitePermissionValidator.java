/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quite.system.validation.validator;

import com.gitee.quite.system.entity.QuitePermission;
import com.gitee.quite.system.validation.annotation.QuitePermissionCheck;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验权限信息的实体属性.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuitePermissionValidator implements ConstraintValidator<QuitePermissionCheck, QuitePermission> {
    
    @Override
    public boolean isValid(QuitePermission value, ConstraintValidatorContext context) {
        boolean valid = false;
        if (value != null) {
            // @formatter:off
            valid = StringUtils.isNotBlank(value.getPostAuthorizeValue()) ||
                    StringUtils.isNotBlank(value.getPostFilterValue()) ||
                    StringUtils.isNotBlank(value.getPreAuthorizeValue()) ||
                    StringUtils.isNotBlank(value.getPreFilterValue());
            // @formatter:on
        }
        return valid;
    }
}
