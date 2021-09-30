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

import com.gitee.quiet.service.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 项目配置信息 VO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocProjectConfigVO extends BaseVO {
    
    /**
     * 配置名称
     */
    @NotEmpty
    @Length(max = 30)
    private String name;
    
    /**
     * 请求路径
     */
    @Length(max = 90)
    private String baseUrl;
    
    /**
     * 项目ID
     */
    @NotNull
    private Long projectId;
    
    /**
     * 备注
     */
    @Length(max = 100)
    private String remark;
    
}
