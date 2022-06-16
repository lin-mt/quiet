/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.doc.controller;

import com.gitee.quiet.doc.converter.DocProjectConvert;
import com.gitee.quiet.doc.dto.DocProjectDTO;
import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.doc.service.DocProjectService;
import com.gitee.quiet.doc.vo.DocProjectVO;
import com.gitee.quiet.doc.vo.MyDocProject;
import com.gitee.quiet.doc.vo.ProjectApiInfo;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.service.utils.CurrentUserUtil;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
        return Result.success(projectApiInfo);
    }

    /**
     * 根据项目ID查询项目信息
     *
     * @param id 项目ID
     * @return 项目信息
     */
    @GetMapping("/{id}")
    public Result<DocProjectVO> projectInfo(@PathVariable Long id) {
        DocProject docProject = projectService.getById(id);
        return Result.success(projectConvert.entity2vo(docProject));
    }

    /**
     * 新建项目文档
     *
     * @param dto 新建的项目信息
     * @return 新增的项目信息
     */
    @PostMapping
    public Result<DocProjectVO> save(@RequestBody @Validated(Create.class) DocProjectDTO dto) {
        DocProject save = projectService.save(projectConvert.dto2entity(dto));
        return Result.createSuccess(projectConvert.entity2vo(save));
    }

    /**
     * 更新项目信息
     *
     * @param dto 更新的项目信息
     * @return 更新后的项目信息
     */
    @PutMapping
    public Result<DocProjectVO> update(@RequestBody @Validated(Update.class) DocProjectDTO dto) {
        DocProject update = projectService.update(projectConvert.dto2entity(dto));
        return Result.updateSuccess(projectConvert.entity2vo(update));
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
    @GetMapping("/my-project")
    public Result<MyDocProject> myDocProject() {
        return Result.success(projectService.getProjectByUserId(CurrentUserUtil.getId()));
    }
}
