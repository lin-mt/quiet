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
import com.gitee.quiet.common.validation.group.param.IdValid;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.scrum.entity.ScrumIteration;
import com.gitee.quiet.scrum.params.ScrumIterationParam;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 迭代 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/iteration")
public class ScrumIterationController {
    
    private final ScrumIterationService iterationService;
    
    public ScrumIterationController(ScrumIterationService iterationService) {
        this.iterationService = iterationService;
    }
    
    /**
     * 新建迭代
     *
     * @param param :save 新建的迭代信息
     * @return 新建后的迭代信息
     */
    @PostMapping("/save")
    public Result<ScrumIteration> save(@RequestBody @Validated(Create.class) ScrumIterationParam param) {
        return Result.createSuccess(iterationService.save(param.getSave()));
    }
    
    /**
     * 更新迭代信息
     *
     * @param param :update 更新的迭代信息
     * @return 更新后的迭代信息
     */
    @PostMapping("/update")
    public Result<ScrumIteration> update(@RequestBody @Validated(Update.class) ScrumIterationParam param) {
        return Result.updateSuccess(iterationService.update(param.getUpdate()));
    }
    
    /**
     * 开始迭代
     *
     * @param param :id 开始迭代的迭代ID
     * @return 开始迭代的迭代信息
     */
    @PostMapping("/start")
    public Result<ScrumIteration> start(@RequestBody @Validated(IdValid.class) ScrumIterationParam param) {
        return Result.updateSuccess(iterationService.start(param.getId()));
    }
    
    /**
     * 结束迭代
     *
     * @param param :id 结束迭代的迭代ID
     * @return 结束迭代的迭代信息
     */
    @PostMapping("/end")
    public Result<ScrumIteration> end(@RequestBody @Validated(IdValid.class) ScrumIterationParam param) {
        return Result.updateSuccess(iterationService.end(param.getId()));
    }
    
    /**
     * 删除迭代
     *
     * @param param :deleteId 删除的迭代ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) ScrumIterationParam param) {
        iterationService.deleteById(param.getDeleteId());
        return Result.deleteSuccess();
    }
}
