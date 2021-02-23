/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quiet.system.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.ParamsNotNull;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.gitee.quiet.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.params.QuietTeamParam;
import com.gitee.quiet.system.service.QuietTeamService;
import com.querydsl.core.QueryResults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 团队 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/team")
public class QuietTeamController {
    
    private final QuietTeamService teamService;
    
    public QuietTeamController(QuietTeamService teamService) {
        this.teamService = teamService;
    }
    
    /**
     * 根据团队名称查询团队信息
     *
     * @param postParam 查询参数
     * @return 团队信息
     */
    @PostMapping("/listTeamsByTeamName")
    public Result<List<QuietTeam>> listTeamsByTeamName(
            @RequestBody @Validated(ParamsNotNull.class) QuietTeamParam postParam) {
        return Result.success(teamService.listTeamsByTeamName(postParam.getParams().getTeamName(), 9));
    }
    
    /**
     * 分页查询团队信息.
     *
     * @return 团队信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietTeam>> page(@RequestBody QuietTeamParam param) {
        return Result.success(teamService.page(param.getParams(), param.page()));
    }
    
    /**
     * 新增团队.
     *
     * @param param :save 新增的团队信息
     * @return 新增后的团队信息
     */
    @PostMapping("/save")
    public Result<QuietTeam> save(@RequestBody @Validated(Create.class) QuietTeamParam param) {
        return Result.createSuccess(teamService.saveOrUpdate(param.getSave()));
    }
    
    /**
     * 删除团队.
     *
     * @param param :deleteId 删除的团队ID
     * @return Result
     */
    @PostMapping("/delete")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietTeamParam param) {
        teamService.deleteTeam(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 更新团队.
     *
     * @param param :update 更新的团队信息
     * @return 新增后的团队信息
     */
    @PostMapping("/update")
    public Result<QuietTeam> update(@RequestBody @Validated(Update.class) QuietTeamParam param) {
        return Result.updateSuccess(teamService.saveOrUpdate(param.getUpdate()));
    }
    
}
