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

import com.gitee.quiet.doc.entity.DocApi;
import java.util.Collection;
import java.util.List;

/**
 * Api Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocApiService {

    /**
     * 根据项目ID查询接口信息
     *
     * @param projectId 项目ID
     * @return 接口信息
     */
    List<DocApi> listAllByProjectId(Long projectId);

    /**
     * 移除接口中的分组
     *
     * @param groupId 移除的分组的ID
     */
    void removeGroup(Long groupId);

    /**
     * 新增接口信息
     *
     * @param save 新增的接口信息
     * @return 新增后的接口信息
     */
    DocApi save(DocApi save);

    /**
     * 更新接口信息
     *
     * @param update 更新的接口信息
     * @return 更新后的接口信息
     */
    DocApi update(DocApi update);

    /**
     * 根据接口ID删除接口信息
     *
     * @param id 接口ID
     */
    void deleteById(Long id);

    /**
     * 查询接口详细信息
     *
     * @param id 接口ID
     * @return 接口详细信息
     */
    DocApi getById(Long id);

    /**
     * 校验id是否存在
     *
     * @param id 要校验的ID
     */
    void checkId(Long id);

    /**
     * 批量保存
     *
     * @param docApis 文档信息
     */
    void saveAll(Collection<DocApi> docApis);
}
