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
import com.gitee.quiet.common.validation.group.IdValid;
import com.gitee.quiet.common.validation.group.Update;
import com.gitee.quiet.scrum.convert.ScrumIterationConvert;
import com.gitee.quiet.scrum.dto.ScrumIterationDto;
import com.gitee.quiet.scrum.entity.ScrumIteration;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 迭代 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/iteration")
public class ScrumIterationController {
    
    private final ScrumIterationService iterationService;
    
    private final ScrumIterationConvert iterationConvert;
    
    /**
     * 新建迭代
     *
     * @param dto 新建的迭代信息
     * @return 新建后的迭代信息
     */
    @PostMapping
    public Result<ScrumIteration> save(@RequestBody @Validated(Create.class) ScrumIterationDto dto) {
        return Result.createSuccess(iterationService.save(iterationConvert.dtoToEntity(dto)));
    }
    
    /**
     * 更新迭代信息
     *
     * @param dto 更新的迭代信息
     * @return 更新后的迭代信息
     */
    @PutMapping
    public Result<ScrumIteration> update(@RequestBody @Validated(Update.class) ScrumIterationDto dto) {
        return Result.updateSuccess(iterationService.update(iterationConvert.dtoToEntity(dto)));
    }
    
    /**
     * 开始迭代
     *
     * @param dto :id 开始迭代的迭代ID
     * @return 开始迭代的迭代信息
     */
    @PostMapping("/start")
    public Result<ScrumIteration> start(@RequestBody @Validated(IdValid.class) ScrumIterationDto dto) {
        return Result.updateSuccess(iterationService.start(dto.getId()));
    }
    
    /**
     * 结束迭代
     *
     * @param dto :id 结束迭代的迭代ID
     * @return 结束迭代的迭代信息
     */
    @PostMapping("/end")
    public Result<ScrumIteration> end(@RequestBody @Validated(IdValid.class) ScrumIterationDto dto) {
        return Result.updateSuccess(iterationService.end(dto.getId()));
    }
    
    /**
     * 删除迭代
     *
     * @param id 删除的迭代ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        iterationService.deleteById(id);
        return Result.deleteSuccess();
    }
}
