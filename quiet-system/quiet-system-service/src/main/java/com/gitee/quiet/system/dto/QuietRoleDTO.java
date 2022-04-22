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

import com.gitee.quiet.service.dto.ParentDTO;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 角色.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietRoleDTO extends ParentDTO<QuietRoleDTO> {

    /**
     * 角色名称
     */
    @NotBlank
    @Length(max = 30)
    private String roleName;

    /**
     * 角色中文名
     */
    @NotBlank
    @Length(max = 30)
    private String roleCnName;

    /**
     * 备注
     */
    @Length(max = 100)
    private String remark;

    /**
     * 父角色名称
     */
    @Transient
    private String parentRoleName;

}
