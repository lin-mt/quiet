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
import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.batch.UpdateBatch;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import com.gitee.quiet.scrum.params.ScrumTaskStepParam;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 任务步骤 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/taskStep")
public class ScrumTaskStepController {
    
    private final ScrumTaskStepService taskStepService;
    
    public ScrumTaskStepController(ScrumTaskStepService taskStepService) {
        this.taskStepService = taskStepService;
    }
    
    /**
     * 新增任务步骤
     *
     * @param param :save 新增的任务步骤信息
     * @return 新增后的任务步骤信息
     */
    @PostMapping("/save")
    public Result<ScrumTaskStep> save(@RequestBody @Validated(Create.class) ScrumTaskStepParam param) {
        return Result.createSuccess(taskStepService.save(param.getSave()));
    }
    
    /**
     * 更新任务步骤
     *
     * @param params :update 更新的任务步骤信息
     * @return 更新后的任务步骤信息
     */
    @PostMapping("/update")
    public Result<ScrumTaskStep> update(@RequestBody @Validated(Update.class) ScrumTaskStepParam params) {
        return Result.updateSuccess(taskStepService.update(params.getUpdate()));
    }
    
    /**
     * 删除任务步骤
     *
     * @param param :deleteId 删除的任务步骤ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) ScrumTaskStepParam param) {
        taskStepService.deleteById(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 批量更新任务步骤
     *
     * @param param :updateBatch 批量更新的步骤信息
     * @return 更新结果
     */
    @PostMapping("/updateBatch")
    public Result<List<ScrumTaskStep>> updateBatch(@RequestBody @Validated(UpdateBatch.class) ScrumTaskStepParam param) {
        return Result.success(taskStepService.updateBatch(param.getUpdateBatch()));
    }
    
    /**
     * 根据模板ID查询该模板ID下的所有任务步骤配置信息
     *
     * @param param 模板ID
     * @return 模板下的所有任务步骤配置信息
     */
    @PostMapping("getAllByTemplateId")
    public Result<List<ScrumTaskStep>> getAllByTemplateId(@RequestBody ScrumTaskStepParam param) {
        if (param.getTemplateId() == null) {
            throw new ServiceException("controller.taskStep.templateId.notNull");
        }
        return Result.success(taskStepService.findAllByTemplateId(param.getTemplateId()));
    }
}
