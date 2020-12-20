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

package com.gitee.quiet.common.service.id;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ID 生成器配置.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@ConfigurationProperties(prefix = "quiet.id-generator")
public class IdGeneratorProperties {
    
    private Integer workerId = 0;
    
    private Integer dataCenterId = 0;
    
    public Integer getWorkerId() {
        return workerId;
    }
    
    public void setWorkerId(Integer workerId) {
        this.workerId = workerId == null ? 0 : workerId;
    }
    
    public Integer getDataCenterId() {
        return dataCenterId;
    }
    
    public void setDataCenterId(Integer dataCenterId) {
        this.dataCenterId = dataCenterId == null ? 0 : dataCenterId;
    }
}
