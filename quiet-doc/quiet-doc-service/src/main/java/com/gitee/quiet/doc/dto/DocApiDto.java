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

package com.gitee.quiet.doc.dto;

import com.gitee.quiet.common.service.dto.SerialDto;
import com.gitee.quiet.common.service.util.CurrentUserUtil;
import com.gitee.quiet.doc.enums.ApiState;
import com.gitee.quiet.doc.enums.HttpMethod;
import com.gitee.quiet.system.entity.QuietUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * 文档信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocApiDto extends SerialDto {
    
    /**
     * 接口名称
     */
    @NotBlank
    @Length(max = 30)
    private String name;
    
    /**
     * 项目ID
     */
    @NotNull
    private Long projectId;
    
    /**
     * 接口状态
     */
    @NotNull
    private ApiState apiState = ApiState.UNFINISHED;
    
    /**
     * 请求地址
     */
    @NotBlank
    @Length(max = 300)
    private String url;
    
    /**
     * 请求方法
     */
    @NotNull
    private HttpMethod method;
    
    /**
     * 作者ID
     */
    private Long authorId = CurrentUserUtil.getId();
    
    /**
     * 所属分组ID
     */
    @Size(max = 30)
    private Set<Long> apiGroupIds;
    
    /**
     * 访问者用户ID
     */
    @Size(max = 30)
    private Set<Long> visitorIds;
    
    /**
     * 备注
     */
    @Length(max = 300)
    private String remark;
    
    /**
     * 访问者信息
     */
    @Transient
    private List<QuietUser> visitors;
    
}
