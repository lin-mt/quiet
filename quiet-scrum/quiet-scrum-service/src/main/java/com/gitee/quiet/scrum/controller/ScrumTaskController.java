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
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.scrum.entity.ScrumTask;
import com.gitee.quiet.scrum.params.ScrumTaskParam;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/task")
public class ScrumTaskController {
    
    private final ScrumTaskService taskService;
    
    public ScrumTaskController(ScrumTaskService taskService) {
        this.taskService = taskService;
    }
    
    /**
     * 查询需求的所有任务信息
     *
     * @param param 查询参数
     * @return 根据需求ID以及任务步骤ID分组后的任务集合
     */
    @PostMapping("/findAllTaskByDemandIds")
    public Result<Map<Long, Map<Long, List<ScrumTask>>>> findAllTaskByDemandIds(@RequestBody ScrumTaskParam param) {
        return Result.success(taskService.findAllTaskByDemandIds(param.getDemandIds()));
    }
    
    /**
     * 创建任务
     *
     * @param param :save 创建的任务信息
     * @return 创建后的任务信息
     */
    @PostMapping("/save")
    public Result<ScrumTask> save(@RequestBody @Validated(Create.class) ScrumTaskParam param) {
        return Result.success(taskService.save(param.getSave()));
    }
    
    /**
     * 更新任务
     *
     * @param param :update 更新的任务信息
     * @return 更新后的任务信息
     */
    @PostMapping("/update")
    public Result<ScrumTask> update(@RequestBody @Validated(Update.class) ScrumTaskParam param) {
        return Result.success(taskService.update(param.getUpdate()));
    }
    
    /**
     * 删除任务
     *
     * @param param :deleteId 删除的任务ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) ScrumTaskParam param) {
        taskService.deleteById(param.getDeleteId());
        return Result.deleteSuccess();
    }
}
