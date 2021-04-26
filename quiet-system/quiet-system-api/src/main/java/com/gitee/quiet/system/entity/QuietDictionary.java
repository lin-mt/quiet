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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import com.querydsl.core.BooleanBuilder;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static com.gitee.quiet.system.entity.QQuietDictionary.quietDictionary;

/**
 * 数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_dictionary", uniqueConstraints = {
        @UniqueConstraint(name = "unique_type_key", columnNames = {"dictionary_type", "dictionary_key"})})
public class QuietDictionary extends Dictionary {
    
    @Length(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Nullable
    @Override
    public BooleanBuilder booleanBuilder() {
        // @formatter:off
        return SelectBuilder.booleanBuilder()
                .notNullEq(getId(), quietDictionary.id)
                .notBlankContains(getType(), quietDictionary.type)
                .notBlankContains(getKey(), quietDictionary.key)
                .notBlankContains(getRemark(), quietDictionary.remark)
                .notNullEq(getParentId(), quietDictionary.parentId).getPredicate();
        // @formatter:on
    }
}
