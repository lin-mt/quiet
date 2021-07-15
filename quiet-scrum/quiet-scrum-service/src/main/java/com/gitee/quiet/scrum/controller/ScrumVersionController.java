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
import com.gitee.quiet.common.validation.group.Create;
import com.gitee.quiet.common.validation.group.Update;
import com.gitee.quiet.scrum.convert.ScrumVersionConvert;
import com.gitee.quiet.scrum.dto.ScrumVersionDto;
import com.gitee.quiet.scrum.entity.ScrumVersion;
import com.gitee.quiet.scrum.service.ScrumVersionService;
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
 * 项目版本信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/version")
public class ScrumVersionController {
    
    private final ScrumVersionService versionService;
    
    private final ScrumVersionConvert versionConvert;
    
    /**
     * 查询项目的所有版本信息，包含迭代信息
     *
     * @param id 项目ID
     * @return 项目的所有版本信息，包含各个版本的迭代信息
     */
    @GetMapping("/all/{id}")
    public Result<List<ScrumVersion>> all(@PathVariable Long id) {
        return Result.success(versionService.findDetailsByProjectId(id));
    }
    
    /**
     * 新建版本
     *
     * @param dto 新建的版本信息
     * @return 新建后的版本信息
     */
    @PostMapping
    public Result<ScrumVersion> save(@RequestBody @Validated(Create.class) ScrumVersionDto dto) {
        return Result.createSuccess(versionService.save(versionConvert.dtoToEntity(dto)));
    }
    
    /**
     * 更新版本信息
     *
     * @param dto 更新的版本信息
     * @return 更新后的版本信息
     */
    @PutMapping
    public Result<ScrumVersion> update(@RequestBody @Validated(Update.class) ScrumVersionDto dto) {
        return Result.updateSuccess(versionService.update(versionConvert.dtoToEntity(dto)));
    }
    
    /**
     * 删除版本
     *
     * @param id 删除的版本ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        versionService.deleteById(id);
        return Result.deleteSuccess();
    }
}
