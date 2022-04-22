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

import com.gitee.quiet.scrum.entity.ScrumVersion;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 项目版本信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumVersionService {

    /**
     * 根据项目ID删除版本信息
     *
     * @param projectId 要删除版本信息的项目ID
     */
    void deleteAllByProjectId(@NotNull Long projectId);

    /**
     * v 查询项目的版本信息，版本中包含迭代信息
     *
     * @param projectId 要查询的项目ID
     * @return 版本信息
     */
    List<ScrumVersion> findDetailsByProjectId(@NotNull Long projectId);

    /**
     * 新建版本信息
     *
     * @param save 新建的版本信息
     * @return 新建后的版本信息
     */
    ScrumVersion save(ScrumVersion save);

    /**
     * 更新版本信息
     *
     * @param update 更新的版本信息
     * @return 更新后的版本信息
     */
    ScrumVersion update(ScrumVersion update);

    /**
     * 根据id判断版本是否存在，不存在则抛出ServiceException异常
     *
     * @param id 版本ID
     */
    void checkIdExist(Long id);

    /**
     * 根据版本id删除版本信息
     *
     * @param id 版本ID
     */
    void deleteById(Long id);

    /**
     * 查询下一个版本信息
     *
     * @param id 当前版本ID
     * @return 下一个版本信息
     */
    ScrumVersion nextVersion(Long id);
}
