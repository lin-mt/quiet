/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quite.system.service;

import com.gitee.quite.system.entity.QuiteDepartment;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 部门Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteDepartmentService {
    
    /**
     * 分页查询部门数据
     *
     * @param params 查询条件
     * @param page   分页参数
     * @return 查询结果
     */
    QueryResults<QuiteDepartment> page(QuiteDepartment params, Pageable page);
    
    /**
     * 保存或者更新部门数据
     *
     * @param department 保存或者更新的部门ID
     */
    QuiteDepartment saveOrUpdate(QuiteDepartment department);
    
    /**
     * 删除部门数据
     *
     * @param deleteId 要删除的部门ID
     */
    void delete(Long deleteId);
    
    /**
     * 获取所有部门的树形结构信息
     *
     * @return 所有部门信息
     */
    List<QuiteDepartment> tree();
}
