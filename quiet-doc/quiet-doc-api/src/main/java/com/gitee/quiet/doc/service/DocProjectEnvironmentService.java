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

package com.gitee.quiet.doc.service;

import com.gitee.quiet.doc.entity.DocProjectEnvironment;
import java.util.List;

/**
 * 项目环境 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocProjectEnvironmentService {

    /**
     * 根据项目ID查询项目所有的环境配置信息
     *
     * @param projectId 项目ID
     * @return 项目所有的环境配置信息
     */
    List<DocProjectEnvironment> listByProjectId(Long projectId);

    /**
     * 创建项目环境
     *
     * @param save 环境信息
     * @return 创建的环境信息
     */
    DocProjectEnvironment save(DocProjectEnvironment save);

    /**
     * 更新项目环境
     *
     * @param update 环境信息
     * @return 更新的环境信息
     */
    DocProjectEnvironment update(DocProjectEnvironment update);

    /**
     * 根据ID删除数据
     *
     * @param id 要删除的数据的id
     */
    void deleteById(Long id);

    /**
     * 根据环境ID查询项目环境配置信息
     *
     * @param id 环境ID
     * @return 环境配置信息
     */
    DocProjectEnvironment getById(Long id);
}
