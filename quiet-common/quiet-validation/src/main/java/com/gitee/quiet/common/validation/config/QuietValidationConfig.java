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

package com.gitee.quiet.common.validation.config;

import com.gitee.quiet.common.base.utils.MessageSourceUtil;
import com.gitee.quiet.common.validation.advice.ValidationExceptionAdvice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * validation 配置类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@AutoConfigureBefore(ValidationAutoConfiguration.class)
public class QuietValidationConfig {
    
    public static final String QUIET_VALIDATION_MESSAGE_SOURCE = "quietValidationMessageSource";
    
    @Bean(name = QuietValidationConfig.QUIET_VALIDATION_MESSAGE_SOURCE)
    public MessageSource messageSource(MessageSourceProperties properties) {
        properties.setBasename("quiet-validation,validation");
        return MessageSourceUtil.buildMessageSource(properties);
    }
    
    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(
            @Qualifier(QuietValidationConfig.QUIET_VALIDATION_MESSAGE_SOURCE) MessageSource messageSource) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setValidationMessageSource(messageSource);
        return factoryBean;
    }
    
    @Bean
    public ValidationExceptionAdvice validationExceptionAdvice() {
        return new ValidationExceptionAdvice();
    }
}
