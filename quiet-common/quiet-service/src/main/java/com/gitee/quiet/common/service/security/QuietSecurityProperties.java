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

package com.gitee.quiet.common.service.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * 安全配置信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@ConfigurationProperties(prefix = "quiet.security")
public class QuietSecurityProperties {
    
    /**
     * 不需要校验权限的 url
     */
    private Set<String> ignoreUrls;
    
    public Set<String> getIgnoreUrls() {
        return ignoreUrls;
    }
    
    public void setIgnoreUrls(Set<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }
}
