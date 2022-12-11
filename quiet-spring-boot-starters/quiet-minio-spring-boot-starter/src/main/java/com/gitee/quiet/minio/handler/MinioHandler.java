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

package com.gitee.quiet.minio.handler;

import com.gitee.quiet.minio.response.FileResponse;
import io.minio.StatObjectResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface MinioHandler {

  default void beforeUpload(String classification, List<MultipartFile> files) {}

  default List<FileResponse> afterUpload(List<FileResponse> responses) {
    return responses;
  }

  default void beforeView(String object) {}

  default void beforeDownloadGetObject(String object) {}

  default StatObjectResponse beforeDownload(StatObjectResponse response) {
    return response;
  }

  default void beforeDelete(String object) {}

  default void afterDelete(String object) {}

  default void beforeGetDetail(String object) {}

  default FileResponse afterGetDetail(FileResponse response) {
    return response;
  }
}
