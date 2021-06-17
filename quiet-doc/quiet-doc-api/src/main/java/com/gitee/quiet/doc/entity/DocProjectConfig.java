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

import com.gitee.quiet.common.service.jpa.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * 项目配置信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "doc_project_config")
public class DocProjectConfig extends BaseEntity {
    
    /**
     * 配置名称
     */
    @NotEmpty
    @Length(max = 30)
    @Column(name = "config_name", nullable = false, length = 30)
    private String name;
    
    /**
     * 域名
     */
    @NotEmpty
    @Length(max = 50)
    @Column(name = "domain_name", nullable = false, length = 50)
    private String domainName;
    
    /**
     * 前缀
     */
    @Length(max = 30)
    @Column(name = "url_prefix", length = 30)
    private String urlPrefix;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDomainName() {
        return domainName;
    }
    
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
    
    public String getUrlPrefix() {
        return urlPrefix;
    }
    
    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }
}
