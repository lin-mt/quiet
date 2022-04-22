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

import com.gitee.quiet.scrum.convert.ScrumIterationConvert;
import com.gitee.quiet.scrum.dto.ScrumIterationDTO;
import com.gitee.quiet.scrum.entity.ScrumIteration;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import com.gitee.quiet.scrum.vo.ScrumIterationVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.IdValid;
import com.gitee.quiet.validation.groups.Update;
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
    public Result<ScrumIterationVO> save(@RequestBody @Validated(Create.class) ScrumIterationDTO dto) {
        ScrumIteration save = iterationService.save(iterationConvert.dto2entity(dto));
        return Result.createSuccess(iterationConvert.entity2vo(save));
    }

    /**
     * 更新迭代信息
     *
     * @param dto 更新的迭代信息
     * @return 更新后的迭代信息
     */
    @PutMapping
    public Result<ScrumIterationVO> update(@RequestBody @Validated(Update.class) ScrumIterationDTO dto) {
        ScrumIteration update = iterationService.update(iterationConvert.dto2entity(dto));
        return Result.updateSuccess(iterationConvert.entity2vo(update));
    }

    /**
     * 开始迭代
     *
     * @param dto :id 开始迭代的迭代ID
     * @return 开始迭代的迭代信息
     */
    @PostMapping("/start")
    public Result<ScrumIterationVO> start(@RequestBody @Validated(IdValid.class) ScrumIterationDTO dto) {
        ScrumIteration iteration = iterationService.start(dto.getId());
        return Result.updateSuccess(iterationConvert.entity2vo(iteration));
    }

    /**
     * 结束迭代
     *
     * @param dto :id 结束迭代的迭代ID
     * @return 结束迭代的迭代信息
     */
    @PostMapping("/end")
    public Result<ScrumIterationVO> end(@RequestBody @Validated(IdValid.class) ScrumIterationDTO dto) {
        ScrumIteration iteration = iterationService.end(dto.getId());
        return Result.updateSuccess(iterationConvert.entity2vo(iteration));
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
