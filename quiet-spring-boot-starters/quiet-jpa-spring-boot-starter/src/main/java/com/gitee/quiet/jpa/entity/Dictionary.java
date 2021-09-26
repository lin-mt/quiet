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

package com.gitee.quiet.jpa.entity;

import com.gitee.quiet.common.constant.service.DictionarySplit;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@QueryEntity
@MappedSuperclass
public class Dictionary<T extends Dictionary<T>> extends ParentAndSerialEntity<T> implements Serializable {
    
    /**
     * 数据字典类型
     */
    @Length(max = 30)
    @Column(name = "dictionary_type", nullable = false, length = 30)
    private String type;
    
    /**
     * 数据字典的key，同数据字典类型下的key不能重复，这个要在业务代码中进行限制
     */
    @Length(max = 30)
    @Column(name = "dictionary_key", length = 30)
    private String key;
    
    /**
     * 数据字典显示的值，前端找不到国际化值的时候使用的默认值
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "label", nullable = false, length = 30)
    private String label;
    
    public Dictionary() {
    }
    
    public Dictionary(String type, String key) {
        this.type = type;
        this.key = key;
    }
    
    /**
     * String 转为 数据字典
     *
     * @param dictionaryStr 要转换的字符串
     * @return 转换后的数据字典
     */
    @Nullable
    public static <T extends Dictionary<T>> Dictionary<T> convertFromString(String dictionaryStr) {
        if (StringUtils.isNotBlank(dictionaryStr)) {
            String[] split = dictionaryStr.split(DictionarySplit.REGEX);
            if (split.length < DictionarySplit.ARRAY_MIN_LENGTH) {
                throw new IllegalArgumentException("数据库数据字典有误，数据字典必须包含 type 和 key");
            }
            Dictionary<T> dictionary = new Dictionary<>();
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
    public static <T extends Dictionary<T>> String convertToString(Dictionary<T> dictionary) {
        if (dictionary != null) {
            if (!StringUtils.isNotBlank(dictionary.getType()) || !StringUtils.isNotBlank(dictionary.getKey())) {
                throw new IllegalArgumentException("数据字典的 type 和 key 都不能为空");
            }
            return dictionary.getType() + DictionarySplit.SEPARATOR + dictionary.getKey();
        }
        return null;
    }
    
    @Override
    public String toString() {
        return Dictionary.convertToString(this);
    }
}



