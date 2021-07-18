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

package com.gitee.quiet.doc.entity;

import com.gitee.quiet.common.service.jpa.entity.SerialEntity;
import com.gitee.quiet.doc.enums.ApiState;
import com.gitee.quiet.doc.enums.HttpMethod;
import com.gitee.quiet.system.entity.QuietUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
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
@Entity
@Table(name = "doc_api")
public class DocApi extends SerialEntity {
    
    /**
     * 接口名称
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "api_name", nullable = false, length = 30)
    private String name;
    
    /**
     * 项目ID
     */
    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    
    /**
     * 接口状态
     */
    @NotNull
    @Column(name = "api_state", nullable = false, length = 10)
    private ApiState apiState;
    
    /**
     * 请求地址
     */
    @NotBlank
    @Length(max = 300)
    @Column(name = "url", nullable = false, length = 300)
    private String url;
    
    /**
     * 请求方法
     */
    @NotNull
    @Length(max = 7)
    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, length = 7)
    private HttpMethod method;
    
    /**
     * 作者ID
     */
    @NotNull
    @Column(name = "author_id", nullable = false)
    private Long authorId;
    
    /**
     * 所属分组ID
     */
    @Size(max = 30)
    @Column(name = "api_group_id", length = 570)
    private Set<Long> apiGroupIds;
    
    /**
     * 访问者用户ID
     */
    @Size(max = 30)
    @Column(name = "visitor_id", length = 570)
    private Set<Long> visitorIds;
    
    /**
     * 备注
     */
    @Length(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;
    
    /**
     * 访问者信息
     */
    @Transient
    private List<QuietUser> visitors;
    
}
