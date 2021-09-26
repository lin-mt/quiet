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

package com.gitee.quiet.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitee.quiet.common.constant.service.RoleNames;
import com.gitee.quiet.service.dto.BaseDTO;
import com.gitee.quiet.service.enums.Gender;
import com.gitee.quiet.service.json.annotation.JsonHasRole;
import com.gitee.quiet.system.entity.QuietUserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 用户信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietUserDTO extends BaseDTO {
    
    /**
     * 用户名
     */
    @NotBlank
    @Length(max = 10)
    private String username;
    
    /**
     * 全名（姓名）
     */
    @NotBlank
    @Length(max = 10)
    private String fullName;
    
    /**
     * 头像地址
     */
    @Length(max = 100)
    private String avatar;
    
    /**
     * 密码
     */
    @NotBlank
    @Length(max = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String secretCode;
    
    /**
     * 性别
     */
    private Gender gender;
    
    /**
     * 电话号码（手机号码）
     */
    @Pattern(regexp = "^1\\d{10}$", message = "{user.phoneNumber.wrong}")
    @Length(min = 11, max = 11, message = "{user.phoneNumber.wrong}")
    private String phoneNumber;
    
    /**
     * 邮箱地址
     */
    @Email
    private String emailAddress;
    
    /**
     * 账号是否过期
     */
    @ColumnDefault("0")
    @JsonHasRole(RoleNames.Admin)
    private Boolean accountExpired;
    
    /**
     * 账号是否被锁
     */
    @JsonHasRole(RoleNames.Admin)
    private Boolean accountLocked;
    
    /**
     * 密码是否过期
     */
    @JsonHasRole(RoleNames.Admin)
    private Boolean credentialsExpired;
    
    /**
     * 账号是否启用
     */
    @JsonHasRole(RoleNames.Admin)
    private Boolean enabled;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 用户与角色信息
     */
    private List<QuietUserRole> userRoles;
    
    @JsonIgnore
    public String getSecretCode() {
        return secretCode;
    }
}
