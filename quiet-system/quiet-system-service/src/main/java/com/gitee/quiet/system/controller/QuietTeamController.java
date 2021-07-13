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

package com.gitee.quiet.system.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.Create;
import com.gitee.quiet.common.validation.group.Update;
import com.gitee.quiet.system.convert.QuietTeamConvert;
import com.gitee.quiet.system.dto.QuietTeamDto;
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.service.QuietTeamService;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * 团队 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/team")
public class QuietTeamController {
    
    private final QuietTeamService teamService;
    
    private final QuietTeamConvert teamConvert;
    
    /**
     * 根据团队名称查询团队信息
     *
     * @param dto :teamName 团队名称
     * @return 团队信息
     */
    @GetMapping("/listTeamsByTeamName")
    public Result<List<QuietTeam>> listTeamsByTeamName(QuietTeamDto dto) {
        return Result.success(teamService.listTeamsByTeamName(dto.getTeamName(), 9));
    }
    
    /**
     * 分页查询团队信息.
     *
     * @param dto 查询参数
     * @return 团队信息
     */
    @GetMapping("/page")
    public Result<QueryResults<QuietTeam>> page(QuietTeamDto dto) {
        return Result.success(teamService.page(teamConvert.dtoToEntity(dto), dto.page()));
    }
    
    /**
     * 新增团队.
     *
     * @param dto 新增的团队信息
     * @return 新增后的团队信息
     */
    @PostMapping
    public Result<QuietTeam> save(@RequestBody @Validated(Create.class) QuietTeamDto dto) {
        return Result.createSuccess(teamService.saveOrUpdate(teamConvert.dtoToEntity(dto)));
    }
    
    /**
     * 删除团队.
     *
     * @param id 删除的团队ID
     * @return Result
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 更新团队.
     *
     * @param dto 更新的团队信息
     * @return 新增后的团队信息
     */
    @PutMapping
    public Result<QuietTeam> update(@RequestBody @Validated(Update.class) QuietTeamDto dto) {
        return Result.updateSuccess(teamService.saveOrUpdate(teamConvert.dtoToEntity(dto)));
    }
    
}
