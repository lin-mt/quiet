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
import com.gitee.quiet.common.service.util.SpringSecurityUtils;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.gitee.quiet.scrum.MyScrumProject;
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.params.ScrumProjectParam;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/project")
public class ScrumProjectController {
    
    private final ScrumProjectService projectService;
    
    public ScrumProjectController(ScrumProjectService projectService) {
        this.projectService = projectService;
    }
    
    /**
     * 查询当前登陆人的所有项目
     *
     * @return 项目信息
     */
    @PostMapping("/allMyProjects")
    public Result<MyScrumProject> allMyProjects() {
        return Result.success(projectService.allProjectByUserId(SpringSecurityUtils.getCurrentUserId()));
    }
    
    /**
     * 新增项目
     *
     * @param param :save 新增的项目信息
     * @return 新增后的项目信息
     */
    @PostMapping("/save")
    public Result<ScrumProject> save(@RequestBody @Validated(Create.class) ScrumProjectParam param) {
        return Result.createSuccess(projectService.save(param.getSave()));
    }
    
    /**
     * 更新项目
     *
     * @param param :update 更新的项目信息
     * @return 更新后的项目信息
     */
    @PostMapping("/update")
    public Result<ScrumProject> update(@RequestBody @Validated(Update.class) ScrumProjectParam param) {
        return Result.updateSuccess(projectService.update(param.getUpdate()));
    }
    
}
