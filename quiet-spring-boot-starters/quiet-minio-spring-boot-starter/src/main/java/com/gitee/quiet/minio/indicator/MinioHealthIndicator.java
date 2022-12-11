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

package com.gitee.quiet.minio.indicator;

import com.gitee.quiet.minio.properties.MinioConfigurationProperties;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
@AllArgsConstructor
@ConditionalOnClass(ManagementContextAutoConfiguration.class)
public class MinioHealthIndicator implements HealthIndicator {

  private final MinioClient minioClient;
  private final MinioConfigurationProperties properties;

  @Override
  public Health health() {
    if (minioClient == null) {
      return Health.down().build();
    }
    String bucketName = properties.getBucketName();
    try {
      BucketExistsArgs args = BucketExistsArgs.builder().bucket(properties.getBucketName()).build();
      if (minioClient.bucketExists(args)) {
        return Health.up().withDetail("bucketName", bucketName).build();
      } else {
        return Health.down().withDetail("bucketName", bucketName).build();
      }
    } catch (Exception e) {
      return Health.down(e).withDetail("bucketName", bucketName).build();
    }
  }
}
