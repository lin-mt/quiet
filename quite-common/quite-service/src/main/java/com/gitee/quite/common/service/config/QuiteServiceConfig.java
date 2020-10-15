package com.gitee.quite.common.service.config;

import com.gitee.quite.common.service.advice.ApplicationExceptionAdvice;
import com.gitee.quite.common.service.advice.ResultAdvice;
import com.gitee.quite.common.service.id.IdGenerator;
import com.gitee.quite.common.service.jackson.deserializer.DataDictionaryDeserializer;
import com.gitee.quite.common.service.jackson.deserializer.LongDeserializer;
import com.gitee.quite.common.service.jackson.serializer.DictionarySerializer;
import com.gitee.quite.common.service.jackson.serializer.LongSerializer;
import com.gitee.quite.common.service.util.ApplicationUtil;
import com.gitee.quite.common.service.util.SnowFlakeIdWorker;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Duration;

/**
 * 所有服务的共同配置信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@EnableConfigurationProperties(IdGenerator.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class QuiteServiceConfig {
    
    public static final String QUITE_COMMON_MESSAGE_SOURCE = "quiteCommonMessageSource";
    
    public static final String QUITE_DICTIONARY_MESSAGE_SOURCE = "quiteDictionaryMessageSource";
    
    @Bean
    public ApplicationUtil applicationUtil() {
        return new ApplicationUtil();
    }
    
    @Bean
    public SnowFlakeIdWorker snowFlakeIdWorker(IdGenerator idGenerator) {
        return new SnowFlakeIdWorker(idGenerator.getWorkerId(), idGenerator.getDataCenterId());
    }
    
    @Bean(QUITE_COMMON_MESSAGE_SOURCE)
    public MessageSource commonMessageSource(MessageSourceProperties properties) {
        return buildMessageSource(properties, "quite-common");
    }
    
    @Bean(QUITE_DICTIONARY_MESSAGE_SOURCE)
    public MessageSource dictionaryMessageSource(MessageSourceProperties properties) {
        return buildMessageSource(properties, "quite-enum-dictionary", "quite-dictionary");
    }
    
    @Bean
    public LocalValidatorFactoryBean getValidator(MessageSourceProperties properties) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean
                .setValidationMessageSource(buildMessageSource(properties, "quite-validation", "validation"));
        return localValidatorFactoryBean;
    }
    
    private MessageSource buildMessageSource(MessageSourceProperties properties, String... basenames) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(basenames);
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
    
    @Bean
    public ApplicationExceptionAdvice applicationExceptionAdvice() {
        return new ApplicationExceptionAdvice();
    }
    
    @Bean
    public ResultAdvice<?> resultAdvice() {
        return new ResultAdvice<>();
    }
    
    @Bean
    public DataDictionaryDeserializer<?> databaseDictionaryDeserializer() {
        return new DataDictionaryDeserializer<>();
    }
    
    @Bean
    public DictionarySerializer<?> dictionarySerializer() {
        return new DictionarySerializer<>();
    }
    
    @Bean
    public LongSerializer longSerializer() {
        return new LongSerializer();
    }
    
    @Bean
    public LongDeserializer longDeserializer() {
        return new LongDeserializer();
    }
}
