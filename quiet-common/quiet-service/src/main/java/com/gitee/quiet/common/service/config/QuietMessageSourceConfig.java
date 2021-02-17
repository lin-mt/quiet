package com.gitee.quiet.common.service.config;

import com.gitee.quiet.common.base.utils.MessageSourceUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MessageSource 配置类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
public class QuietMessageSourceConfig {
    
    public static final String QUIET_COMMON_MESSAGE_SOURCE = "quietCommonMessageSource";
    
    @Bean
    @ConditionalOnMissingBean(value = MessageSourceProperties.class)
    public MessageSourceProperties messageSourceProperties() {
        return new MessageSourceProperties();
    }
    
    @Bean(QUIET_COMMON_MESSAGE_SOURCE)
    public MessageSource commonMessageSource(MessageSourceProperties properties) {
        return MessageSourceUtil.buildMessageSource(properties, "quiet-common");
    }
    
}
