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
