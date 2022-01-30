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

import com.gitee.quiet.scrum.convert.ScrumTaskStepConvert;
import com.gitee.quiet.scrum.dto.ScrumTaskStepDTO;
import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import com.gitee.quiet.scrum.vo.ScrumTaskStepVO;
import com.gitee.quiet.service.dto.ValidListDTO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务步骤 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/task-step")
public class ScrumTaskStepController {
    
    private final ScrumTaskStepService taskStepService;
    
    private final ScrumTaskStepConvert taskStepConvert;
    
    /**
     * 新增任务步骤
     *
     * @param dto 新增的任务步骤信息
     * @return 新增后的任务步骤信息
     */
    @PostMapping
    public Result<ScrumTaskStepVO> save(@RequestBody @Validated(Create.class) ScrumTaskStepDTO dto) {
        ScrumTaskStep save = taskStepService.save(taskStepConvert.dto2entity(dto));
        return Result.createSuccess(taskStepConvert.entity2vo(save));
    }
    
    /**
     * 更新任务步骤
     *
     * @param dto 更新的任务步骤信息
     * @return 更新后的任务步骤信息
     */
    @PutMapping
    public Result<ScrumTaskStepVO> update(@RequestBody @Validated(Update.class) ScrumTaskStepDTO dto) {
        ScrumTaskStep update = taskStepService.update(taskStepConvert.dto2entity(dto));
        return Result.updateSuccess(taskStepConvert.entity2vo(update));
    }
    
    /**
     * 删除任务步骤
     *
     * @param id 删除的任务步骤ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        taskStepService.deleteById(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 批量更新任务步骤
     *
     * @param dto :data 批量更新的步骤信息
     * @return 更新结果
     */
    @PutMapping("/batch")
    public Result<List<ScrumTaskStepVO>> updateBatch(
            @RequestBody @Validated(Update.class) ValidListDTO<ScrumTaskStepDTO> dto) {
        List<ScrumTaskStep> batch = taskStepService.updateBatch(
                dto.getData().stream().map(taskStepConvert::dto2entity).collect(Collectors.toList()));
        return Result.success(taskStepConvert.entities2vos(batch));
    }
    
    /**
     * 根据模板ID查询该模板ID下的所有任务步骤配置信息
     *
     * @param id 模板ID
     * @return 模板下的所有任务步骤配置信息
     */
    @GetMapping("/all-by-template-id/{id}")
    public Result<List<ScrumTaskStepVO>> findAllByTemplateId(@PathVariable Long id) {
        List<ScrumTaskStep> taskSteps = taskStepService.findAllByTemplateId(id);
        return Result.success(taskStepConvert.entities2vos(taskSteps));
    }
}
