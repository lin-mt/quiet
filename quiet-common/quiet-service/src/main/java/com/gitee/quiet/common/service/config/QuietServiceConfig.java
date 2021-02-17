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

package com.gitee.quiet.common.service.config;

import com.gitee.quiet.common.base.constant.RoleNames;
import com.gitee.quiet.common.service.advice.ApplicationExceptionAdvice;
import com.gitee.quiet.common.service.advice.ResultAdvice;
import com.gitee.quiet.common.service.id.IdGeneratorProperties;
import com.gitee.quiet.common.service.jackson.deserializer.LongDeserializer;
import com.gitee.quiet.common.service.jackson.serializer.LongSerializer;
import com.gitee.quiet.common.service.util.ApplicationUtil;
import com.gitee.quiet.common.service.util.SnowFlakeIdWorker;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

/**
 * 所有服务的共同配置信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@EnableDiscoveryClient
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(IdGeneratorProperties.class)
public class QuietServiceConfig {
    
    @Bean
    public ApplicationUtil applicationUtil() {
        return new ApplicationUtil();
    }
    
    @Bean
    public SnowFlakeIdWorker snowFlakeIdWorker(IdGeneratorProperties properties) {
        return new SnowFlakeIdWorker(properties.getWorkerId(), properties.getDataCenterId());
    }
    
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(RoleNames.ROLE_PREFIX);
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
    public LongSerializer longSerializer() {
        return new LongSerializer();
    }
    
    @Bean
    public LongDeserializer longDeserializer() {
        return new LongDeserializer();
    }
}

