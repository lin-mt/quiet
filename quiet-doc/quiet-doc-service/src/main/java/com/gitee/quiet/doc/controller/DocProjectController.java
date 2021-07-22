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
import com.gitee.quiet.common.validation.group.Create;
import com.gitee.quiet.common.validation.group.Update;
import com.gitee.quiet.doc.converter.DocProjectConvert;
import com.gitee.quiet.doc.dto.DocProjectDto;
import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.doc.service.DocProjectService;
import com.gitee.quiet.doc.vo.MyDocProject;
import com.gitee.quiet.doc.vo.ProjectApiInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Project Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class DocProjectController {
    
    private final DocProjectService projectService;
    
    private final DocApiGroupService apiGroupService;
    
    private final DocApiService apiService;
    
    private final DocProjectConvert projectConvert;
    
    /**
     * 根据项目ID查询所有接口分组信息，包含接口信息
     *
     * @param id 项目ID
     * @return 项目所有接口分组信息
     */
    @GetMapping("/apis/{id}")
    public Result<ProjectApiInfo> apis(@PathVariable Long id) {
        List<DocApiGroup> apiGroups = apiGroupService.listByProjectId(id);
        Map<Long, DocApiGroup> apiGroupIdToInfo = apiGroups.stream()
                .collect(Collectors.toMap(DocApiGroup::getId, a -> a));
        ProjectApiInfo projectApiInfo = new ProjectApiInfo();
        projectApiInfo.getApiGroups().addAll(apiGroups);
        if (CollectionUtils.isNotEmpty(apiGroups)) {
            List<DocApi> apis = apiService.listAllByProjectId(id);
            if (CollectionUtils.isNotEmpty(apis)) {
                List<DocApi> ungroup = new ArrayList<>();
                Map<Long, List<DocApi>> grouped = new HashMap<>();
                for (DocApi api : apis) {
                    if (api.getApiGroupId() == null) {
                        ungroup.add(api);
                        continue;
                    }
                    grouped.computeIfAbsent(api.getApiGroupId(), k -> new ArrayList<>());
                    grouped.get(api.getApiGroupId()).add(api);
                    api.setApiGroup(apiGroupIdToInfo.get(api.getApiGroupId()));
                }
                projectApiInfo.getUngroup().addAll(ungroup);
                projectApiInfo.getGrouped().putAll(grouped);
            }
        }
        return Result.success(projectApiInfo);
    }
    
    /**
     * 根据项目ID查询项目信息
     *
     * @param id 项目ID
     * @return 项目信息
     */
    @GetMapping("/{id}")
    public Result<DocProject> projectInfo(@PathVariable Long id) {
        return Result.success(projectService.getById(id));
    }
    
    /**
     * 新建项目文档
     *
     * @param dto 新建的项目信息
     * @return 新增的项目信息
     */
    @PostMapping
    public Result<DocProject> save(@RequestBody @Validated(Create.class) DocProjectDto dto) {
        return Result.success(projectService.save(projectConvert.dtoToEntity(dto)));
    }
    
    /**
     * 更新项目信息
     *
     * @param dto 更新的项目信息
     * @return 更新后的项目信息
     */
    @PutMapping
    public Result<DocProject> update(@RequestBody @Validated(Update.class) DocProjectDto dto) {
        return Result.success(projectService.update(projectConvert.dtoToEntity(dto)));
    }
    
    /**
     * 根据项目ID删除项目信息
     *
     * @param id 要删除的项目ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        projectService.delete(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 获取登陆人可访问的项目信息
     *
     * @return 可访问的项目信息
     */
    @GetMapping("/myProject")
    public Result<MyDocProject> myDocProjectResult() {
        return Result.success(projectService.getProjectByUserId(CurrentUserUtil.getId()));
    }
}
