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

import com.gitee.quiet.scrum.convert.ScrumPriorityConvert;
import com.gitee.quiet.scrum.dto.ScrumPriorityDTO;
import com.gitee.quiet.scrum.entity.ScrumPriority;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import com.gitee.quiet.scrum.vo.ScrumPriorityVO;
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
    public Result<ScrumPriorityVO> save(@RequestBody @Validated(Create.class) ScrumPriorityDTO dto) {
        ScrumPriority save = priorityService.save(priorityConvert.dto2entity(dto));
        return Result.createSuccess(priorityConvert.entity2vo(save));
    }

    /**
     * 更新优先级选项信息
     *
     * @param dto 更新的优先级信息
     * @return 更新后的优先级信息
     */
    @PutMapping
    public Result<ScrumPriorityVO> update(@RequestBody @Validated(Update.class) ScrumPriorityDTO dto) {
        ScrumPriority update = priorityService.update(priorityConvert.dto2entity(dto));
        return Result.updateSuccess(priorityConvert.entity2vo(update));
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
    public Result<List<ScrumPriorityVO>> updateBatch(
        @RequestBody @Validated(Update.class) ValidListDTO<ScrumPriorityDTO> dto) {
        List<ScrumPriority> batch = priorityService.updateBatch(
            dto.getData().stream().map(priorityConvert::dto2entity).collect(Collectors.toList()));
        return Result.success(priorityConvert.entities2vos(batch));
    }

    /**
     * 根据模板ID查询该模板ID下的所有优先级配置信息
     *
     * @param id 模板ID
     * @return 模板下的所有优先级配置信息
     */
    @GetMapping("/all-by-template-id/{id}")
    public Result<List<ScrumPriorityVO>> allByTemplateId(@PathVariable Long id) {
        List<ScrumPriority> priorities = priorityService.findAllByTemplateId(id);
        return Result.success(priorityConvert.entities2vos(priorities));
    }
}
