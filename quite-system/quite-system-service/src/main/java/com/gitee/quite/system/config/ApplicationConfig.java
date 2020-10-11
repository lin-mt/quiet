package com.gitee.quite.system.config;

import com.gitee.quite.system.util.ApplicationUtil;
import com.gitee.quite.system.util.SnowFlakeIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Duration;

/**
 * 配置类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
public class ApplicationConfig {
    
    public static final String COMMON_MESSAGE_SOURCE = "commonMessageSource";
    
    @Value("${snow-flake-id-work.worker-id:0}")
    private Long workId;
    
    @Value("${snow-flake-id-work.data-center-id:0}")
    private Long dataCenterId;
    
    @Bean
    public ApplicationUtil applicationUtil() {
        return new ApplicationUtil();
    }
    
    @Bean
    public SnowFlakeIdWorker snowFlakeIdWorker() {
        return new SnowFlakeIdWorker(workId, dataCenterId);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public LocalValidatorFactoryBean getValidator(MessageSourceProperties properties) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(buildMessageSource(properties, "validation"));
        return localValidatorFactoryBean;
    }
    
    @Bean(COMMON_MESSAGE_SOURCE)
    public MessageSource commonMessageSource(MessageSourceProperties properties) {
        return buildMessageSource(properties, "common");
    }
    
    private MessageSource buildMessageSource(MessageSourceProperties properties, String basename) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(basename);
        if (properties.getEncoding() != null) {
            messageSource.setDefaultEncoding(properties.getEncoding().name());
        }
        messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
        Duration cacheDuration = properties.getCacheDuration();
        if (cacheDuration != null) {
            messageSource.setCacheMillis(cacheDuration.toMillis());
        }
        messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
        messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
        return messageSource;
    }
}
