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

import com.gitee.quiet.doc.converter.DocApiGroupConvert;
import com.gitee.quiet.doc.dto.DocApiGroupDTO;
import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.vo.DocApiGroupVO;
import com.gitee.quiet.service.result.Result;
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

import java.util.List;

/**
 * 接口分组信息Api.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api-group")
public class DocApiGroupController {
    
    private final DocApiGroupService apiGroupService;
    
    private final DocApiGroupConvert apiGroupConvert;
    
    /**
     * 新建接口分组
     *
     * @param dto 新建的接口分组信息
     * @return 新增的接口分组信息
     */
    @PostMapping
    public Result<DocApiGroupVO> save(@RequestBody @Validated(Create.class) DocApiGroupDTO dto) {
        DocApiGroup save = apiGroupService.save(apiGroupConvert.dto2entity(dto));
        return Result.createSuccess(apiGroupConvert.entity2vo(save));
    }
    
    /**
     * 更新接口分组信息
     *
     * @param dto 更新的接口分组信息
     * @return 更新后的接口分组信息
     */
    @PutMapping
    public Result<DocApiGroupVO> update(@RequestBody @Validated(Update.class) DocApiGroupDTO dto) {
        DocApiGroup update = apiGroupService.update(apiGroupConvert.dto2entity(dto));
        return Result.updateSuccess(apiGroupConvert.entity2vo(update));
    }
    
    /**
     * 根据接口分组ID删除接口分组ID
     *
     * @param id 要删除的接口分组ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        apiGroupService.deleteById(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 根据项目ID和接口名称模糊查询6条接口分组信息
     *
     * @param dto :projectId 项目ID :name 分组名称
     * @return 接口分组信息
     */
    @GetMapping("/list-by-project-id-and-name")
    public Result<List<DocApiGroupVO>> listByProjectIdAndName(DocApiGroupDTO dto) {
        List<DocApiGroup> docApiGroups = apiGroupService.listByProjectIdAndName(dto.getProjectId(), dto.getName(), 6L);
        return Result.success(apiGroupConvert.entities2vos(docApiGroups));
    }
}
