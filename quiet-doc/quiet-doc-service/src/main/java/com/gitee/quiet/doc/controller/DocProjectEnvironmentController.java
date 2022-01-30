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
 *
 */

package com.gitee.quiet.doc.controller;

import com.gitee.quiet.doc.converter.DocProjectEnvironmentConverter;
import com.gitee.quiet.doc.dto.DocProjectEnvironmentDTO;
import com.gitee.quiet.doc.entity.DocProjectEnvironment;
import com.gitee.quiet.doc.service.DocProjectEnvironmentService;
import com.gitee.quiet.doc.vo.DocProjectEnvironmentVO;
import com.gitee.quiet.service.result.Result;
import lombok.AllArgsConstructor;
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
 * 项目环境接口.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project-environment")
public class DocProjectEnvironmentController {

    private final DocProjectEnvironmentConverter converter;

    private final DocProjectEnvironmentService service;

    /**
     * 创建项目环境
     *
     * @param dto 环境信息
     * @return 创建的环境信息
     */
    @PostMapping
    public Result<DocProjectEnvironmentVO> save(@RequestBody DocProjectEnvironmentDTO dto) {
        DocProjectEnvironment projectEnvironment = service.save(converter.dto2entity(dto));
        return Result.createSuccess(converter.entity2vo(projectEnvironment));
    }

    /**
     * 更新项目环境配置
     *
     * @param dto 环境配置信息
     * @return 更新的环境信息
     */
    @PutMapping
    public Result<DocProjectEnvironmentVO> update(@RequestBody DocProjectEnvironmentDTO dto) {
        DocProjectEnvironment update = service.update(converter.dto2entity(dto));
        return Result.updateSuccess(converter.entity2vo(update));
    }
    
    /**
     * 根据ID删除环境配置
     * @param id 环境配置ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        service.deleteById(id);
        return Result.deleteSuccess();
    }

    /**
     * 根据项目ID查询所有环境配置信息
     *
     * @param projectId 项目ID
     * @return 项目环境配置信息
     */
    @GetMapping("/list-by-project-id/{projectId}")
    public Result<List<DocProjectEnvironmentVO>> listByProjectId(@PathVariable Long projectId) {
        List<DocProjectEnvironment> docProjectEnvironments = service.listByProjectId(projectId);
        return Result.success(converter.entities2vos(docProjectEnvironments));
    }
}
