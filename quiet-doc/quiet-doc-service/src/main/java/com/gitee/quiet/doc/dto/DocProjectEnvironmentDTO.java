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
 *
 */

package com.gitee.quiet.doc.dto;

import com.gitee.quiet.doc.model.Cookie;
import com.gitee.quiet.doc.model.Header;
import com.gitee.quiet.doc.model.HttpProtocol;
import com.gitee.quiet.service.dto.BaseDTO;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 项目环境DTO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocProjectEnvironmentDTO extends BaseDTO {

    /**
     * 环境名称
     */
    @NotEmpty
    @Length(max = 30)
    private String name;

    /**
     * 项目ID
     */
    @NotNull
    private Long projectId;

    /**
     * http协议
     */
    @NotNull
    private HttpProtocol protocol;

    /**
     * 请求路径
     */
    @Length(max = 90)
    private String basePath;

    /**
     * 请求头
     */
    private List<Header> headers;

    /**
     * 请求cookie
     */
    private List<Cookie> cookies;
}
