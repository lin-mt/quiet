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

import com.gitee.quiet.scrum.convert.ScrumDemandConvert;
import com.gitee.quiet.scrum.dto.ScrumDemandDTO;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.IdValid;
import com.gitee.quiet.validation.groups.OffsetLimitValid;
import com.gitee.quiet.validation.groups.Update;
import com.querydsl.core.QueryResults;
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

/**
 * 需求Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/demand")
public class ScrumDemandController {
    
    private final ScrumDemandService demandService;
    
    private final ScrumDemandConvert demandConvert;
    
    /**
     * 删除需求
     *
     * @param id 删除的需求ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        demandService.deleteById(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 滚动查询待规划的需求
     *
     * @param dto :id 项目ID ：demandFilter 需求过滤条件
     * @return 待规划的需求
     */
    @GetMapping("/scrollToBePlanned")
    public Result<List<ScrumDemand>> scrollToBePlanned(
            @Validated({OffsetLimitValid.class, IdValid.class}) ScrumDemandDTO dto) {
        return Result.success(
                demandService.listToBePlanned(dto.getId(), dto.getDemandFilter(), dto.getOffset(), dto.getLimit()));
    }
    
    /**
     * 滚动查询迭代的需求
     *
     * @param dto :id 迭代ID
     * @return 处于该迭代的需求
     */
    @GetMapping("/scrollByIterationId")
    public Result<List<ScrumDemand>> scrollByIterationId(
            @Validated({OffsetLimitValid.class, IdValid.class}) ScrumDemandDTO dto) {
        return Result.success(demandService.scrollIteration(dto.getId(), dto.getOffset(), dto.getLimit()));
    }
    
    /**
     * 创建需求
     *
     * @param dto 创建的需求信息
     * @return 创建后的需求信息
     */
    @PostMapping
    public Result<ScrumDemand> save(@RequestBody @Validated(Create.class) ScrumDemandDTO dto) {
        return Result.success(demandService.save(demandConvert.dtoToEntity(dto)));
    }
    
    /**
     * 更新需求
     *
     * @param dto 更新的需求信息
     * @return 更新后的需求信息
     */
    @PutMapping
    public Result<ScrumDemand> update(@RequestBody @Validated(Update.class) ScrumDemandDTO dto) {
        return Result.success(demandService.update(demandConvert.dtoToEntity(dto)));
    }
    
    /**
     * 查询一个迭代下的所有需求信息
     *
     * @param id 迭代ID
     * @return 需求信息
     */
    @GetMapping("/all/{id}")
    public Result<List<ScrumDemand>> all(@PathVariable Long id) {
        return Result.success(demandService.findAllByIterationId(id));
    }
    
    /**
     * 分页查询需求信息
     *
     * @param dto 查询参数
     * @return 查询结果
     */
    @GetMapping("/page")
    public Result<QueryResults<ScrumDemand>> page(ScrumDemandDTO dto) {
        return Result.success(demandService.page(demandConvert.dtoToEntity(dto), dto.page()));
    }
    
}
