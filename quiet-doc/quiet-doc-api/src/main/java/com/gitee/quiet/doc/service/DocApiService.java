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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
   * 查询接口详细信息
   *
   * @param id 接口ID
   * @return 接口详细信息
   */
  DocApi getById(Long id);

  /**
   * 批量保存
   *
   * @param docApis 文档信息
   */
  void saveAll(Collection<DocApi> docApis);

  /**
   * 根据项目ID和接口名称模糊查询接口信息
   *
   * @param projectId 项目ID
   * @param name 接口名称
   * @param limit 限制查询条数，小于等于0或者不传则查询所有
   * @return 接口信息
   */
  List<DocApi> listByProjectIdAndName(Long projectId, String name, Long limit);

  /**
   * 分页查询接口，api_group_id 传0时会加上 api_group_id is null 过滤条件
   *
   * @param entity 过滤条件
   * @param page 分页参数
   * @return 查询结果
   */
  Page<DocApi> page(DocApi entity, Pageable page);
}
