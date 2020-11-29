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

package com.gitee.quite.system.controller;

import com.gitee.quite.common.service.base.Param;
import com.gitee.quite.common.service.result.Result;
import com.gitee.quite.common.validation.group.curd.Create;
import com.gitee.quite.common.validation.group.curd.Update;
import com.gitee.quite.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quite.system.entity.QuiteTeam;
import com.gitee.quite.system.service.QuiteTeamService;
import com.querydsl.core.QueryResults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 团队 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/team")
public class QuiteTeamController {
    
    private final QuiteTeamService teamService;
    
    public QuiteTeamController(QuiteTeamService teamService) {
        this.teamService = teamService;
    }
    
    /**
     * 分页查询团队信息.
     *
     * @return 团队信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuiteTeam>> page(@RequestBody QuiteTeamParam param) {
        return Result.success(teamService.page(param.getParams(), param.page()));
    }
    
    /**
     * 新增团队.
     *
     * @param param :save 新增的团队信息
     * @return 新增后的团队信息
     */
    @PostMapping("/save")
    public Result<QuiteTeam> save(@RequestBody @Validated(Create.class) QuiteTeamParam param) {
        return Result.createSuccess(teamService.saveOrUpdate(param.getSave()));
    }
    
    /**
     * 删除团队.
     *
     * @param param :deleteId 删除的团队ID
     * @return Result
     */
    @DeleteMapping("/delete")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuiteTeamParam param) {
        teamService.deleteTeam(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 更新团队.
     *
     * @param param :update 更新的团队信息
     * @return 新增后的团队信息
     */
    @PutMapping("/update")
    public Result<QuiteTeam> update(@RequestBody @Validated(Update.class) QuiteTeamParam param) {
        return Result.updateSuccess(teamService.saveOrUpdate(param.getUpdate()));
    }
    
    static class QuiteTeamParam extends Param<QuiteTeam, QuiteTeam> {
        
    }
}
