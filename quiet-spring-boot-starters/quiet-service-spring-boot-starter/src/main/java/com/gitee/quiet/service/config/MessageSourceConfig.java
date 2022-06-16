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
