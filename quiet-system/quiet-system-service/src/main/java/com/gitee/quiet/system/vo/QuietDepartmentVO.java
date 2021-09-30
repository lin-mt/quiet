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

package com.gitee.quiet.system.vo;

import com.gitee.quiet.service.vo.ParentVO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 部门信息 Vo.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
@Getter
@Setter
public class QuietDepartmentVO extends ParentVO<QuietDepartmentVO> {
    
    /**
     * 部门名称
     */
    @NotBlank
    @Length(max = 10)
    private String departmentName;
    
    /**
     * 备注
     */
    @Length(max = 100)
    private String remark;
}
