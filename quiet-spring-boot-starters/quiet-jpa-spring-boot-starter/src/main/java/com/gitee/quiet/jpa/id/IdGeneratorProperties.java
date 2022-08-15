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

package com.gitee.quiet.jpa.id;

import com.gitee.quiet.jpa.utils.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

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
