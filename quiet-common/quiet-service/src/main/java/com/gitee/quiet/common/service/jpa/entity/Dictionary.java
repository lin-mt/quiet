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

import com.gitee.quiet.common.service.constant.ServiceConstant;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.querydsl.core.annotations.QueryEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@QueryEntity
@MappedSuperclass
public class Dictionary extends ParentEntity<Dictionary> implements Serializable {
    
    /**
     * 数据字典类型
     */
    @Column(name = "dictionary_type", nullable = false, length = 30)
    @Length(max = 30, message = "{dictionary.type}{length.max.limit}")
    private String type;
    
    /**
     * 数据字典的key，同数据字典类型下的key不能重复，这个要在业务代码中进行限制
     */
    @Column(name = "dictionary_key", length = 30)
    @Length(max = 30, message = "{dictionary.key}{length.max.limit}")
    private String key;
    
    /**
     * 数据字典显示的值，前端找不到国际化值的时候使用的默认值
     */
    @NotNull(groups = {Create.class, Update.class}, message = "{dictionary.label}{not.null}")
    @Column(name = "label", nullable = false, length = 30)
    @Length(max = 30, message = "{dictionary.label}{length.max.limit}")
    private String label;
    
    /**
     * String 转为 数据字典
     *
     * @param dictionaryStr 要转换的字符串
     * @return 转换后的字符串
     */
    @Nullable
    public static Dictionary convertFromString(String dictionaryStr) {
        if (StringUtils.isNotBlank(dictionaryStr)) {
            String[] split = dictionaryStr.split(ServiceConstant.Dictionary.SPLIT_REGEX);
            if (split.length < ServiceConstant.Dictionary.ARRAY_MIN_LENGTH) {
                throw new IllegalArgumentException("数据库数据字典有误，数据字典必须包含 type 和 key");
            }
            Dictionary dictionary = new Dictionary();
            dictionary.setType(split[0]);
            dictionary.setKey(split[1]);
            return dictionary;
        }
        return null;
    }
    
    /**
     * 数据字典转换为字符串
     *
     * @param dictionary 要转换的数据字典
     * @return 转换后的字符串
     */
    @Nullable
    public static String convertToString(Dictionary dictionary) {
        if (dictionary != null) {
            if (!StringUtils.isNotBlank(dictionary.getType()) || !StringUtils.isNotBlank(dictionary.getKey())) {
                throw new IllegalArgumentException("数据字典的 type 和 key 都不能为空");
            }
            return dictionary.getType() + ServiceConstant.Dictionary.SPLIT + dictionary.getKey();
        }
        return null;
    }
    
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
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String value) {
        this.label = value;
    }
}
