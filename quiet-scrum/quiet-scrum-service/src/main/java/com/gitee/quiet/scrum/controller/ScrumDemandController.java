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
import com.gitee.quiet.common.validation.group.param.IdValid;
import com.gitee.quiet.common.validation.group.param.OffsetLimitValid;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.common.validation.util.ValidationUtils;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.params.ScrumDemandParam;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.querydsl.core.QueryResults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/demand")
public class ScrumDemandController {
    
    private final ScrumDemandService demandService;
    
    public ScrumDemandController(ScrumDemandService demandService) {
        this.demandService = demandService;
    }
    
    /**
     * 删除需求
     *
     * @param param :deleteId 删除的需求ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) ScrumDemandParam param) {
        demandService.deleteById(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 滚动查询待规划的需求
     *
     * @param param :id 项目ID
     * @return 待规划的需求
     */
    @PostMapping("/scrollToBePlanned")
    public Result<List<ScrumDemand>> scrollToBePlanned(
            @RequestBody @Validated({OffsetLimitValid.class, IdValid.class}) ScrumDemandParam param) {
        return Result.success(demandService
                .listToBePlanned(param.getId(), param.getDemandFilter(), param.getOffset(), param.getLimit()));
    }
    
    /**
     * 滚动查询迭代的需求
     *
     * @param param :id 迭代ID
     * @return 处于该迭代的需求
     */
    @PostMapping("/scrollByIterationId")
    public Result<List<ScrumDemand>> scrollByIterationId(
            @RequestBody @Validated({OffsetLimitValid.class, IdValid.class}) ScrumDemandParam param) {
        return Result.success(demandService.scrollIteration(param.getId(), param.getOffset(), param.getLimit()));
    }
    
    /**
     * 创建需求
     *
     * @param param :save 创建的需求信息
     * @return 创建后的需求信息
     */
    @PostMapping("/save")
    public Result<ScrumDemand> save(@RequestBody @Validated(Create.class) ScrumDemandParam param) {
        return Result.success(demandService.save(param.getSave()));
    }
    
    /**
     * 更新需求
     *
     * @param param :update 更新的需求信息
     * @return 更新后的需求信息
     */
    @PostMapping("/update")
    public Result<ScrumDemand> update(@RequestBody @Validated(Update.class) ScrumDemandParam param) {
        return Result.success(demandService.update(param.getUpdate()));
    }
    
    /**
     * 查询一个迭代下的所有需求信息
     *
     * @param param :iterationId 迭代ID
     * @return 需求信息
     */
    @PostMapping("/findAllByIterationId")
    public Result<List<ScrumDemand>> findAllByIterationId(@RequestBody ScrumDemandParam param) {
        ValidationUtils.notNull(param.getIterationId(), "controller.demand.iterationId.canNotNull");
        return Result.success(demandService.findAllByIterationId(param.getIterationId()));
    }
    
    /**
     * 分页查询需求信息
     *
     * @param param 查询参数
     * @return 查询结果
     */
    @PostMapping("/page")
    public Result<QueryResults<ScrumDemand>> page(@RequestBody ScrumDemandParam param) {
        return Result.success(demandService.page(param.getParams(), param.page()));
    }
    
}
