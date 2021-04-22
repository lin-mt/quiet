/*
 * Copyright 2021. lin-mt@outlook.com
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
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.gitee.quiet.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quiet.scrum.entity.ScrumPriority;
import com.gitee.quiet.scrum.params.ScrumPriorityParam;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优先级 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/priority")
public class ScrumPriorityController {
    
    private final ScrumPriorityService priorityService;
    
    public ScrumPriorityController(ScrumPriorityService priorityService) {
        this.priorityService = priorityService;
    }
    
    /**
     * 新增优先级选项
     *
     * @param param :save 新增的优先级信息
     * @return 新增后的优先级信息
     */
    @PostMapping("/save")
    public Result<ScrumPriority> save(@RequestBody @Validated(Create.class) ScrumPriorityParam param) {
        return Result.createSuccess(priorityService.save(param.getSave()));
    }
    
    /**
     * 更新优先级选项信息
     *
     * @param params :update 更新的优先级信息
     * @return 更新后的优先级信息
     */
    @PostMapping("/update")
    public Result<ScrumPriority> update(@RequestBody @Validated(Update.class) ScrumPriorityParam params) {
        return Result.updateSuccess(priorityService.update(params.getUpdate()));
    }
    
    /**
     * 删除优先级信息
     *
     * @param param :deleteId 删除的优先级ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) ScrumPriorityParam param) {
        priorityService.deleteById(param.getDeleteId());
        return Result.deleteSuccess();
    }
}
