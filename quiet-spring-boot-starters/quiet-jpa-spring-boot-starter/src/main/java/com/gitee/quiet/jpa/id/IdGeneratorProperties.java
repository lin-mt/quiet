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

package com.gitee.quiet.jpa.id;

import com.gitee.quiet.jpa.utils.IdWorker;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ID 生成器配置.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@ConfigurationProperties(prefix = "quiet.jpa.id-generator")
public class IdGeneratorProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdGeneratorProperties.class);

    private long workerId = 0L;

    private long dataCenterId = 0L;

    private int maxServerNumber = 10;

    @PostConstruct
    public void checkIsLegal() {
        if (dataCenterId > maxServerNumber) {
            throw new RuntimeException("dataCenterId 不能大于 maxServerNumber");
        }
        // machine ID 0 ~ 1023
        long machineId = getMachineId();
        if (machineId < 0 || machineId > 1023) {
            throw new IllegalArgumentException("machineId 需要在 0 ～ 1023 之间");
        }
        IdGenerator.setIdWorker(new IdWorker(getMachineId()));
    }

    private long getMachineId() {
        return workerId * maxServerNumber + dataCenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        if (workerId < 0) {
            LOGGER.warn("workerId 不能小于 0，当前值：0");
        } else {
            this.workerId = workerId;
        }
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        if (dataCenterId < 0) {
            LOGGER.warn("dataCenterId 不能小于 0，当前值：0");
        } else {
            this.dataCenterId = dataCenterId;
        }
    }

    public int getMaxServerNumber() {
        return maxServerNumber;
    }

    public void setMaxServerNumber(int maxServerNumber) {
        if (maxServerNumber < 1) {
            LOGGER.warn("maxServerNumber 不能小于 1，当前值：10");
        } else {
            this.maxServerNumber = maxServerNumber;
        }
    }
}
