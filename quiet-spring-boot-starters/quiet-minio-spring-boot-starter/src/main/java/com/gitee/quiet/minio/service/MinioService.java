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

package com.gitee.quiet.minio.service;

import com.gitee.quiet.minio.properties.MinioConfigurationProperties;
import com.google.common.collect.Multimap;
import io.minio.*;
import io.minio.messages.Tags;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class MinioService {

  private final MinioClient minioClient;
  private final MinioConfigurationProperties properties;

  @SneakyThrows
  public Tags getTags(Path path) {
    GetObjectTagsArgs args =
        GetObjectTagsArgs.builder()
            .bucket(properties.getBucketName())
            .object(path.toString())
            .build();
    return minioClient.getObjectTags(args);
  }

  @SneakyThrows
  public InputStream get(Path path) {
    GetObjectArgs args =
        GetObjectArgs.builder().bucket(properties.getBucketName()).object(path.toString()).build();
    return minioClient.getObject(args);
  }

  @SneakyThrows
  public StatObjectResponse getMetadata(Path path) {
    StatObjectArgs args =
        StatObjectArgs.builder().bucket(properties.getBucketName()).object(path.toString()).build();
    return minioClient.statObject(args);
  }

  @SneakyThrows
  public void upload(
      Path source,
      InputStream file,
      String contentType,
      Multimap<String, String> userMetadata,
      Multimap<String, String> headers,
      Map<String, String> tags) {
    PutObjectArgs.Builder builder =
        PutObjectArgs.builder().bucket(properties.getBucketName()).object(source.toString()).stream(
            file, file.available(), -1);
    if (userMetadata != null) {
      builder.userMetadata(userMetadata);
    }
    if (headers != null) {
      builder.headers(headers);
    }
    if (tags != null) {
      builder.tags(tags);
    }
    if (StringUtils.isNotBlank(contentType)) {
      builder.contentType(contentType);
    }
    minioClient.putObject(builder.build());
  }

  public void upload(
      Path source,
      InputStream file,
      String contentType,
      Multimap<String, String> userMetadata,
      Multimap<String, String> headers) {
    upload(source, file, contentType, userMetadata, headers, null);
  }

  public void upload(
      Path source, InputStream file, String contentType, Multimap<String, String> userMetadata) {
    upload(source, file, contentType, userMetadata, null);
  }

  public void upload(Path source, InputStream file, String contentType) {
    upload(source, file, contentType, null);
  }

  public void upload(Path source, InputStream file) {
    upload(source, file, null);
  }

  @SneakyThrows
  public void remove(Path source) {
    RemoveObjectArgs args =
        RemoveObjectArgs.builder()
            .bucket(properties.getBucketName())
            .object(source.toString())
            .build();
    minioClient.removeObject(args);
  }
}
