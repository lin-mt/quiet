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
import com.gitee.quiet.common.service.dto.ValidListDto;
import com.gitee.quiet.common.validation.group.Create;
import com.gitee.quiet.common.validation.group.Update;
import com.gitee.quiet.scrum.convert.ScrumPriorityConvert;
import com.gitee.quiet.scrum.dto.ScrumPriorityDto;
import com.gitee.quiet.scrum.entity.ScrumPriority;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
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
 * 优先级 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/priority")
public class ScrumPriorityController {
    
    private final ScrumPriorityService priorityService;
    
    private final ScrumPriorityConvert priorityConvert;
    
    /**
     * 新增优先级选项
     *
     * @param dto 新增的优先级信息
     * @return 新增后的优先级信息
     */
    @PostMapping
    public Result<ScrumPriority> save(@RequestBody @Validated(Create.class) ScrumPriorityDto dto) {
        return Result.createSuccess(priorityService.save(priorityConvert.dtoToEntity(dto)));
    }
    
    /**
     * 更新优先级选项信息
     *
     * @param dto 更新的优先级信息
     * @return 更新后的优先级信息
     */
    @PutMapping
    public Result<ScrumPriority> update(@RequestBody @Validated(Update.class) ScrumPriorityDto dto) {
        return Result.updateSuccess(priorityService.update(priorityConvert.dtoToEntity(dto)));
    }
    
    /**
     * 删除优先级信息
     *
     * @param id 删除的优先级ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        priorityService.deleteById(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 批量更新优先级信息
     *
     * @param dto :data 批量更新的优先级信息
     * @return 更新结果
     */
    @PutMapping("/batch")
    public Result<List<ScrumPriority>> updateBatch(
            @RequestBody @Validated(Update.class) ValidListDto<ScrumPriorityDto> dto) {
        return Result.success(priorityService
                .updateBatch(dto.getData().stream().map(priorityConvert::dtoToEntity).collect(Collectors.toList())));
    }
    
    /**
     * 根据模板ID查询该模板ID下的所有优先级配置信息
     *
     * @param id 模板ID
     * @return 模板下的所有优先级配置信息
     */
    @GetMapping("/allByTemplateId/{id}")
    public Result<List<ScrumPriority>> allByTemplateId(@PathVariable Long id) {
        return Result.success(priorityService.findAllByTemplateId(id));
    }
}
