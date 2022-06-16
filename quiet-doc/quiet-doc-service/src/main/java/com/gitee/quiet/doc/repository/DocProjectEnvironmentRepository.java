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

package com.gitee.quiet.doc.repository;

import com.gitee.quiet.doc.entity.DocProjectEnvironment;
import com.gitee.quiet.jpa.repository.QuietRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 项目环境数据仓库.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocProjectEnvironmentRepository extends QuietRepository<DocProjectEnvironment> {

    /**
     * 根据项目ID查询所有数据
     *
     * @param projectId 项目ID
     * @return 项目ID的所有环境配置
     */
    List<DocProjectEnvironment> findAllByProjectId(Long projectId);

    /**
     * 根据项目ID和项目名称查询信息
     *
     * @param projectId 项目ID
     * @param name      项目名称
     * @return 项目信息
     */
    DocProjectEnvironment findByProjectIdAndName(Long projectId, String name);
}
