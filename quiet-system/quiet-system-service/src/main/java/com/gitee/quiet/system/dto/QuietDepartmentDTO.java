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
import com.gitee.quiet.system.entity.QuietUser;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * 部门信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietDepartmentDTO extends ParentDTO<QuietDepartmentDTO> {

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户信息
     */
    private QuietUser params;

    /**
     * 用户ID集合
     */
    private Set<Long> userIds;

}
