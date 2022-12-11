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

package com.gitee.quiet.minio.config;

import com.gitee.quiet.minio.properties.MinioConfigurationProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
@Configuration
@AllArgsConstructor
@ComponentScan("com.gitee.quiet.minio")
@EnableConfigurationProperties(MinioConfigurationProperties.class)
public class QuietMinioConfiguration {

  private final MinioConfigurationProperties properties;

  @Bean
  public MinioClient minioClient()
      throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
          NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
          XmlParserException, InternalException {
    MinioClient minioClient =
        MinioClient.builder()
            .endpoint(properties.getUrl())
            .credentials(properties.getAccessKey(), properties.getSecretKey())
            .build();
    minioClient.setTimeout(
        properties.getConnectTimeout().toMillis(),
        properties.getWriteTimeout().toMillis(),
        properties.getReadTimeout().toMillis());
    if (properties.isCheckBucket()) {
      String bucketName = properties.getBucketName();
      BucketExistsArgs existsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
      boolean bucketExists = minioClient.bucketExists(existsArgs);
      if (!bucketExists) {
        if (properties.isCreateBucketIfNotExist()) {
          MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
          minioClient.makeBucket(makeBucketArgs);
        } else {
          throw new IllegalStateException("Bucket does not exist: " + bucketName);
        }
      }
    }
    return minioClient;
  }
}
