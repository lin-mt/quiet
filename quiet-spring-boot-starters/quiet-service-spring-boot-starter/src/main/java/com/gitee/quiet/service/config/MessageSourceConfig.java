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

package com.gitee.quiet.service.config;

import com.gitee.quiet.service.utils.MessageSourceUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MessageSource 配置类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration(proxyBeanMethods = false)
public class MessageSourceConfig {
    
    public static final String QUIET_COMMON_MESSAGE_SOURCE = "quietCommonMessageSource";
    
    @Bean(QUIET_COMMON_MESSAGE_SOURCE)
    public MessageSource commonMessageSource(MessageSourceProperties properties) {
        MessageSourceProperties messageSourceProperties = new MessageSourceProperties();
        BeanUtils.copyProperties(properties, messageSourceProperties);
        messageSourceProperties.setBasename("quiet-common");
        return MessageSourceUtil.buildMessageSource(messageSourceProperties);
    }
    
}
