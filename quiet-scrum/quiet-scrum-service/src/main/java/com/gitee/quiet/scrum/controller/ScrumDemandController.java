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

package com.gitee.quiet.scrum.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.ParamsNotNull;
import com.gitee.quiet.common.validation.util.ValidationUtils;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.params.ScrumDemandParam;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 需求Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/demand")
public class ScrumDemandController {
    
    private final ScrumDemandService demandService;
    
    public ScrumDemandController(ScrumDemandService demandService) {
        this.demandService = demandService;
    }
    
    /**
     * 查询一个迭代下的所有需求信息
     *
     * @param param :params:iterationId 迭代ID
     * @return 需求信息
     */
    @PostMapping("/findAllByIteration")
    public Result<List<ScrumDemand>> findAllByIteration(
            @RequestBody @Validated(ParamsNotNull.class) ScrumDemandParam param) {
        ValidationUtils.notNull(param.getParams().getIterationId(), "demand.iterationId.can.notNull");
        return Result.success(demandService.findAllByIteration(param.getParams().getIterationId()));
    }
    
}
