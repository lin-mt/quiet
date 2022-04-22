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

package com.gitee.quiet.scrum.service;

import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.entity.ScrumProjectTeam;

import java.util.List;
import java.util.Set;

/**
 * 项目团队信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumProjectTeamService {

    /**
     * 查询团队负责的所有项目
     *
     * @param teamIds 团队ID集合
     * @return 团队负责的项目信息
     */
    List<ScrumProject> findAllProjectsByTeamIds(Set<Long> teamIds);

    /**
     * 批量保存项目团队关系信息
     *
     * @param projectTeams 要新增的项目团队信息
     * @return 新增后的团队信息
     */
    @SuppressWarnings("UnusedReturnValue")
    List<ScrumProjectTeam> saveAll(List<ScrumProjectTeam> projectTeams);

    /**
     * 根据项目ID删除该项目的所有团队信息
     *
     * @param projectId 要删除团队信息的项目ID
     */
    void deleteAllByProjectId(Long projectId);

    /**
     * 根据项目ID查询所有项目团队信息
     *
     * @param projectIds 项目ID集合
     * @return 项目团队信息
     */
    List<ScrumProjectTeam> findAllByProjectIds(Set<Long> projectIds);
}
