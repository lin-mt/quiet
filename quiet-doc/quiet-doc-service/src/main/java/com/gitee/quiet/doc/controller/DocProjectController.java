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

package com.gitee.quiet.doc.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.service.util.CurrentUserUtil;
import com.gitee.quiet.common.validation.group.param.IdValid;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.params.DocProjectParam;
import com.gitee.quiet.doc.service.DocProjectService;
import com.gitee.quiet.doc.vo.MyDocProject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/project")
public class DocProjectController {
    
    private final DocProjectService projectService;
    
    public DocProjectController(DocProjectService projectService) {
        this.projectService = projectService;
    }
    
    @PostMapping("/projectInfo")
    public Result<DocProject> projectInfo(@RequestBody @Validated(IdValid.class) DocProjectParam param) {
        return Result.success(projectService.getById(param.getId()));
    }
    
    @PostMapping("/save")
    public Result<DocProject> save(@RequestBody @Validated(Create.class) DocProjectParam param) {
        return Result.success(projectService.save(param.getSave()));
    }
    
    @PostMapping("/update")
    public Result<DocProject> update(@RequestBody @Validated(Update.class) DocProjectParam param) {
        return Result.success(projectService.update(param.getUpdate()));
    }
    
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) DocProjectParam param) {
        projectService.delete(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
    @PostMapping("/myProject")
    public Result<MyDocProject> myDocProjectResult() {
        return Result.success(projectService.getProjectByUserId(CurrentUserUtil.getId()));
    }
}
