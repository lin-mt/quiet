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

import com.gitee.quiet.scrum.convert.ScrumProjectConvert;
import com.gitee.quiet.scrum.dto.ScrumProjectDTO;
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.vo.MyScrumProject;
import com.gitee.quiet.scrum.vo.ScrumProjectDetail;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.service.utils.CurrentUserUtil;
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

/**
 * 项目Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class ScrumProjectController {
    
    private final ScrumProjectService projectService;
    
    private final ScrumProjectConvert projectConvert;
    
    /**
     * 获取项目信息
     *
     * @param id 项目ID
     * @return 项目信息
     */
    @GetMapping("/{id}")
    public Result<ScrumProject> projectInfo(@PathVariable Long id) {
        return Result.success(projectService.projectInfo(id));
    }
    
    /**
     * 获取项目的详细信息
     *
     * @param id 项目ID
     * @return 项目详细信息
     */
    @GetMapping("/detail/{id}")
    public Result<ScrumProjectDetail> detail(@PathVariable Long id) {
        return Result.success(projectService.getDetail(id));
    }
    
    /**
     * 查询当前登陆人的所有项目
     *
     * @return 项目信息
     */
    @GetMapping("/allMyProjects")
    public Result<MyScrumProject> allMyProjects() {
        return Result.success(projectService.allProjectByUserId(CurrentUserUtil.getId()));
    }
    
    /**
     * 新增项目
     *
     * @param dto 新增的项目信息
     * @return 新增后的项目信息
     */
    @PostMapping
    public Result<ScrumProject> save(@RequestBody @Validated(Create.class) ScrumProjectDTO dto) {
        return Result.createSuccess(projectService.save(projectConvert.dtoToEntity(dto)));
    }
    
    /**
     * 更新项目
     *
     * @param dto 更新的项目信息
     * @return 更新后的项目信息
     */
    @PutMapping
    public Result<ScrumProject> update(@RequestBody @Validated(Update.class) ScrumProjectDTO dto) {
        return Result.updateSuccess(projectService.update(projectConvert.dtoToEntity(dto)));
    }
    
    /**
     * 删除项目
     *
     * @param id 删除的项目ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        projectService.deleteById(id);
        return Result.deleteSuccess();
    }
    
}
