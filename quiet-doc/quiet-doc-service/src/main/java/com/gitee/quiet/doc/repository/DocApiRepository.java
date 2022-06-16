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

import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.jpa.repository.QuietRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 接口文档Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocApiRepository extends QuietRepository<DocApi> {

    /**
     * 根据项目ID查询所有接口文档
     *
     * @param projectId 项目ID
     * @return 接口文档信息
     */
    List<DocApi> findAllByProjectId(Long projectId);

    /**
     * 根据项目ID和接口名称查询接口文档信息
     *
     * @param projectId 项目ID
     * @param name      接口名称
     * @return 接口文档信息
     */
    DocApi findByProjectIdAndName(Long projectId, String name);

    /**
     * 根据分组ID查询在该分组的所有接口文档
     *
     * @param groupId 分组ID
     * @return 接口文档信息
     */
    List<DocApi> findAllByApiGroupId(Long groupId);
}
