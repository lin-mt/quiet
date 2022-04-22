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

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.service.dto.BaseDTO;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 路由信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietRouteDTO extends BaseDTO {

    /**
     * 网关的路由ID
     */
    @NotBlank
    @Length(max = 60)
    private String routeId;

    /**
     * 环境，用于批量修改发布
     */
    @NotNull
    private Dictionary<?> environment;

    /**
     * 路由目标
     */
    @NotBlank
    @Length(max = 200)
    private String uri;

    /**
     * 排序
     */
    private int order;

    /**
     * 匹配规则
     */
    private Set<String> predicates;

    /**
     * 过滤器
     */
    private Set<String> filters;

    /**
     * 备注
     */
    @Length(max = 300)
    private String remark;

    /**
     * 匹配规则
     */
    private String routePredicate;

    /**
     * 路由过滤器
     */
    private String routeFilter;

}
