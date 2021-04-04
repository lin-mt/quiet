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

package com.gitee.quiet.common.service.jpa.entity;

import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据字典. // todo 支持 QueryDsl
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@MappedSuperclass
public class Dictionary extends ParentEntity<Dictionary> implements Serializable {
    
    /**
     * 数据字典类型
     */
    @NotNull(groups = {Create.class, Update.class}, message = "{dictionary.type}{not.null}")
    @Column(name = "dictionary_type", nullable = false, length = 30)
    @Length(max = 30, message = "{dictionary.type}{length.max.limit}")
    private String type;
    
    /**
     * 数据字典的key，同数据字典类型下的key不能重复，这个要在业务代码中进行限制
     */
    @Column(name = "dictionary_key", nullable = false, length = 30)
    @Length(max = 30, message = "{dictionary.key}{length.max.limit}")
    private String key;
    
    /**
     * 数据字典显示的值，前端找不到国际化值的时候使用的默认值
     */
    @NotNull(groups = {Create.class, Update.class}, message = "{dictionary.value}{not.null}")
    @Column(name = "dictionary_value", unique = true, nullable = false, length = 30)
    @Length(max = 30, message = "{dictionary.value}{length.max.limit}")
    private String value;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
