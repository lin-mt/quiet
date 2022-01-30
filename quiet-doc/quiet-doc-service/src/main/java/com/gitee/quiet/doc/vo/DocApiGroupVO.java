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
import com.gitee.quiet.service.vo.front.SelectVO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Api 分组信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocApiGroupVO extends SerialVO implements SelectVO<Long> {
    
    /**
     * 分组名称
     */
    @NotBlank
    @Length(max = 30)
    private String name;
    
    /**
     * 所属项目ID
     */
    @NotNull
    private Long projectId;
    
    @Override
    public Long getValue() {
        return getId();
    }
    
    @Override
    public String getLabel() {
        return getName();
    }
}
