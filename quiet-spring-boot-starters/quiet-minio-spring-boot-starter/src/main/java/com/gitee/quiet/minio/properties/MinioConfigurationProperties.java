/*
 *     Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.minio.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Set;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "quiet.minio")
public class MinioConfigurationProperties implements InitializingBean {

  private String url = "http://localhost:7000";

  private String accessKey;

  private String secretKey;

  private String bucketName;

  private Set<String> classifications;

  private String objectPrefix;

  private Duration connectTimeout = Duration.ofSeconds(10);

  private Duration writeTimeout = Duration.ofSeconds(60);

  private Duration readTimeout = Duration.ofSeconds(10);

  private boolean checkBucket = true;

  private boolean createBucketIfNotExist = true;

  @Override
  public void afterPropertiesSet() {
    Assert.hasText(accessKey, "accessKey must not be empty.");
    Assert.hasText(secretKey, "secretKey must not be empty.");
    Assert.hasText(bucketName, "bucketName must not be empty.");
    Assert.hasText(objectPrefix, "objectPrefix must not be empty.");
  }
}
