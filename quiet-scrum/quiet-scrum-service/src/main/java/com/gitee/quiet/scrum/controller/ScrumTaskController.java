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
import com.gitee.quiet.common.validation.group.Create;
import com.gitee.quiet.common.validation.group.Update;
import com.gitee.quiet.scrum.convert.ScrumTaskConvert;
import com.gitee.quiet.scrum.dto.ScrumTaskDto;
import com.gitee.quiet.scrum.entity.ScrumTask;
import com.gitee.quiet.scrum.service.ScrumTaskService;
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
import java.util.Map;

/**
 * 任务Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class ScrumTaskController {
    
    private final ScrumTaskService taskService;
    
    private final ScrumTaskConvert taskConvert;
    
    /**
     * 查询需求的所有任务信息
     *
     * @param dto :demandIds 查询的需求ID集合
     * @return 根据需求ID以及任务步骤ID分组后的任务集合
     */
    @GetMapping("/allTaskByDemandIds")
    public Result<Map<Long, Map<Long, List<ScrumTask>>>> allTaskByDemandIds(ScrumTaskDto dto) {
        return Result.success(taskService.findAllTaskByDemandIds(dto.getDemandIds()));
    }
    
    /**
     * 创建任务
     *
     * @param dto 创建的任务信息
     * @return 创建后的任务信息
     */
    @PostMapping
    public Result<ScrumTask> save(@RequestBody @Validated(Create.class) ScrumTaskDto dto) {
        return Result.success(taskService.save(taskConvert.dtoToEntity(dto)));
    }
    
    /**
     * 更新任务
     *
     * @param dto 更新的任务信息
     * @return 更新后的任务信息
     */
    @PutMapping
    public Result<ScrumTask> update(@RequestBody @Validated(Update.class) ScrumTaskDto dto) {
        return Result.success(taskService.update(taskConvert.dtoToEntity(dto)));
    }
    
    /**
     * 删除任务
     *
     * @param id 删除的任务ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        taskService.deleteById(id);
        return Result.deleteSuccess();
    }
}
