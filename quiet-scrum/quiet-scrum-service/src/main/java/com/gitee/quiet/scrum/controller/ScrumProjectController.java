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
import com.gitee.quiet.common.service.util.CurrentUserUtil;
import com.gitee.quiet.common.validation.group.param.IdValid;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.params.ScrumProjectParam;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.vo.MyScrumProject;
import com.gitee.quiet.scrum.vo.ScrumProjectDetail;
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
     * 获取项目信息
     *
     * @param param :id 项目ID
     * @return 项目信息
     */
    @PostMapping("/projectInfo")
    public Result<ScrumProject> projectInfo(@RequestBody @Validated(IdValid.class) ScrumProjectParam param) {
        return Result.success(projectService.projectInfo(param.getId()));
    }
    
    /**
     * 获取项目的详细信息
     *
     * @param param 查询参数
     * @return 项目详细信息
     */
    @PostMapping("/detail")
    public Result<ScrumProjectDetail> detail(@RequestBody @Validated(IdValid.class) ScrumProjectParam param) {
        return Result.success(projectService.getDetail(param.getId()));
    }
    
    /**
     * 查询当前登陆人的所有项目
     *
     * @return 项目信息
     */
    @PostMapping("/allMyProjects")
    public Result<MyScrumProject> allMyProjects() {
        return Result.success(projectService.allProjectByUserId(CurrentUserUtil.getId()));
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
    
    /**
     * 删除项目
     *
     * @param param :deleteId 删除的项目ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) ScrumProjectParam param) {
        projectService.deleteById(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
}
