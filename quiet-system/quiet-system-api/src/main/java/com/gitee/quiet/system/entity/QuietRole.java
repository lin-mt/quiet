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

import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.security.entity.QuietGrantedAuthority;
import com.querydsl.core.BooleanBuilder;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static com.gitee.quiet.system.entity.QQuietRole.quietRole;

/**
 * 角色.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_role")
public class QuietRole extends QuietGrantedAuthority<QuietRole> {

    /**
     * 角色中文名
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "role_cn_name", nullable = false, length = 30)
    private String roleCnName;

    /**
     * 备注
     */
    @Length(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;

    /**
     * 父角色名称
     */
    @Transient
    private String parentRoleName;

    @Override
    @Transient
    public String getAuthority() {
        return getRoleName();
    }

    @Nullable
    @Override
    public BooleanBuilder booleanBuilder() {
        // @formatter:off
        return SelectBuilder.booleanBuilder()
            .notNullEq(getId(), quietRole.id)
            .notNullEq(getParentId(), quietRole.parentId)
            .notBlankContains(getRoleName(), quietRole.roleName)
            .notBlankContains(getRoleCnName(), quietRole.roleCnName)
            .notBlankContains(getRemark(), quietRole.remark)
            .getPredicate();
        // @formatter:on
    }
}
