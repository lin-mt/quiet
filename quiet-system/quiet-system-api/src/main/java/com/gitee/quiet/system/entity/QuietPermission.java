/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.entity;

import com.gitee.quiet.jpa.entity.base.BaseEntity;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.querydsl.core.BooleanBuilder;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static com.gitee.quiet.system.entity.QQuietPermission.quietPermission;

/**
 * 权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_permission")
public class QuietPermission extends BaseEntity {

    /**
     * 应用名称
     */
    @NotBlank
    @Length(max = 100)
    @Column(name = "application_name", nullable = false, length = 100)
    private String applicationName;

    /**
     * URL 匹配规则
     */
    @NotBlank
    @Length(max = 100)
    @Column(name = "url_pattern", length = 100)
    private String urlPattern;

    /**
     * 请求方法
     */
    @NotBlank
    @Length(max = 7)
    @Column(name = "request_method", length = 7)
    private String requestMethod;

    /**
     * 角色ID
     */
    @NotNull
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    /**
     * 备注
     */
    @Length(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;

    @Nullable
    @Override
    public BooleanBuilder booleanBuilder() {
        // @formatter:off
        return SelectBuilder.booleanBuilder()
            .notNullEq(getId(), quietPermission.id)
            .notNullEq(getRoleId(), quietPermission.roleId)
            .notBlankEq(getRequestMethod(), quietPermission.requestMethod)
            .notBlankContains(getApplicationName(), quietPermission.applicationName)
            .notBlankContains(getUrlPattern(), quietPermission.urlPattern)
            .notBlankContains(getRemark(), quietPermission.remark)
            .getPredicate();
        // @formatter:on
    }
}
