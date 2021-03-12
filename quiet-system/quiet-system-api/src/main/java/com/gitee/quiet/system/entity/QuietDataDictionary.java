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

import com.gitee.quiet.common.service.base.DataDictionary;
import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.querydsl.core.BooleanBuilder;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.gitee.quiet.system.entity.QQuietDataDictionary.quietDataDictionary;

/**
 * 数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_data_dictionary")
public class QuietDataDictionary extends DataDictionary {
    
    @Column(name = "remark", length = 100)
    @Length(max = 100, message = "{dataDictionary.remark}{length.max.limit}")
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
                .notNullEq(getId(), quietDataDictionary.id)
                .notBlankContains(getType(), quietDataDictionary.type)
                .notBlankContains(getKey(), quietDataDictionary.key)
                .notBlankContains(getRemark(), quietDataDictionary.remark)
                .notNullEq(getParentId(), quietDataDictionary.parentId).getPredicate();
        // @formatter:on
    }
}
