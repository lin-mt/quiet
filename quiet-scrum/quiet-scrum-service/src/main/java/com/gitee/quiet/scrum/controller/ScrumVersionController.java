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
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.scrum.entity.ScrumVersion;
import com.gitee.quiet.scrum.params.ScrumVersionParam;
import com.gitee.quiet.scrum.service.ScrumVersionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目版本信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/version")
public class ScrumVersionController {
    
    private final ScrumVersionService versionService;
    
    public ScrumVersionController(ScrumVersionService versionService) {
        this.versionService = versionService;
    }
    
    /**
     * 查询项目的所有版本信息，包含迭代信息
     *
     * @param param :id 项目ID
     * @return 项目的所有版本信息，包含各个版本的迭代信息
     */
    @PostMapping("/findDetailsByProjectId")
    public Result<List<ScrumVersion>> findDetailsByProjectId(
            @RequestBody @Validated(IdValid.class) ScrumVersionParam param) {
        return Result.success(versionService.findDetailsByProjectId(param.getId()));
    }
    
    /**
     * 新建版本
     *
     * @param param :save 新建的版本信息
     * @return 新建后的版本信息
     */
    @PostMapping("/save")
    public Result<ScrumVersion> save(@RequestBody @Validated(Create.class) ScrumVersionParam param) {
        return Result.createSuccess(versionService.save(param.getSave()));
    }
    
    /**
     * 更新版本信息
     *
     * @param param :update 更新的版本信息
     * @return 更新后的版本信息
     */
    @PostMapping("/update")
    public Result<ScrumVersion> update(@RequestBody @Validated(Update.class) ScrumVersionParam param) {
        return Result.updateSuccess(versionService.update(param.getUpdate()));
    }
    
    /**
     * 删除版本
     *
     * @param param :deleteId 删除的版本ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) ScrumVersionParam param) {
        versionService.deleteById(param.getDeleteId());
        return Result.deleteSuccess();
    }
}
