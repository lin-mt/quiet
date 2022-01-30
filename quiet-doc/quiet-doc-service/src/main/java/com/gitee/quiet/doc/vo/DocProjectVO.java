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

package com.gitee.quiet.doc.vo;

import com.gitee.quiet.service.vo.SerialVO;
import com.gitee.quiet.system.entity.QuietUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * 项目信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocProjectVO extends SerialVO {
    
    /**
     * 项目名称
     */
    @NotBlank
    @Length(max = 30)
    private String name;
    
    /**
     * 接口基本路径
     */
    @Length(max = 30)
    private String basePath;
    
    /**
     * 项目文档负责人
     */
    @NotNull
    private Long principal;
    
    /**
     * 访问者用户ID
     */
    @Size(max = 30)
    private Set<Long> visitorIds;
    
    /**
     * 备注
     */
    @Length(max = 100)
    private String remark;
    
    /**
     * 访问者信息
     */
    private List<QuietUser> visitors;
    
    /**
     * 负责人名称
     */
    private String principalName;
    
}
