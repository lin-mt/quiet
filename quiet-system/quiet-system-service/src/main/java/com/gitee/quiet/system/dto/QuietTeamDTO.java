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

import com.gitee.quiet.service.dto.BaseDTO;
import com.gitee.quiet.system.entity.QuietUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 团队.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietTeamDTO extends BaseDTO {
    
    /**
     * 团队名称
     */
    @NotBlank
    @Length(max = 16)
    private String teamName;
    
    /**
     * 标语
     */
    @Length(max = 30)
    private String slogan;
    
    /**
     * 团队PO
     */
    private List<QuietUser> productOwners;
    
    /**
     * 团队SM
     */
    private List<QuietUser> scrumMasters;
    
    /**
     * 团队成员
     */
    private List<QuietUser> members;
}
