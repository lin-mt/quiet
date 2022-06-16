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

import com.gitee.quiet.doc.entity.DocApiGroup;
import java.util.List;

/**
 * Api分组Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocApiGroupService {

    /**
     * 新建接口分组
     *
     * @param save 新建的接口分组
     * @return 新建的接口分组
     */
    DocApiGroup save(DocApiGroup save);

    /**
     * 更新接口分组
     *
     * @param update 更新的接口分组
     * @return 更新后的接口分组
     */
    DocApiGroup update(DocApiGroup update);

    /**
     * 根据接口分组ID删除信息
     *
     * @param id 要删除的接口分组ID
     */
    void deleteById(Long id);

    /**
     * 根据项目ID查询所有接口分组信息
     *
     * @param projectId 项目ID
     * @return 所有接口分组信息
     */
    List<DocApiGroup> listByProjectId(Long projectId);

    /**
     * 根据项目ID和接口名称查询接口分组信息
     *
     * @param projectId 项目ID
     * @param name      分组名称
     * @param limit     查询条数
     * @return 接口分组信息
     */
    List<DocApiGroup> listByProjectIdAndName(Long projectId, String name, long limit);

    /**
     * 根据ID查询分组信息
     *
     * @param id 接口分组ID
     * @return 接口分组信息
     */
    DocApiGroup findById(Long id);
}
